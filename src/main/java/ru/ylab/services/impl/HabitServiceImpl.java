package ru.ylab.services.impl;

import java.util.List;
import java.util.Scanner;

import ru.ylab.App;
import ru.ylab.forms.HabitForm;
import ru.ylab.forms.HabitSearchForm;
import ru.ylab.models.Habit;
import ru.ylab.models.User;
import ru.ylab.repositories.HabitHistoryRepository;
import ru.ylab.repositories.HabitRepository;
import ru.ylab.services.HabitService;
import ru.ylab.utils.IdUtil;
import ru.ylab.utils.InputParser;

/**
 * Service implementing {@link HabitService}.
 *
 * @author azatyamanaev
 */
public class HabitServiceImpl implements HabitService {

    /**
     * Instance of a {@link HabitRepository}
     */
    private final HabitRepository habitRepository;

    /**
     * Instance of a {@link HabitHistoryRepository}
     */
    private final HabitHistoryRepository habitHistoryRepository;

    /**
     * Creates new HabitServiceImpl.
     *
     * @param habitRepository        HabitRepository instance
     * @param habitHistoryRepository HabitHistoryRepository instance
     */
    public HabitServiceImpl(HabitRepository habitRepository, HabitHistoryRepository habitHistoryRepository) {
        this.habitRepository = habitRepository;
        this.habitHistoryRepository = habitHistoryRepository;
    }

    @Override
    public void getHabits(Scanner scanner) {
        System.out.println("Do you want to use filters?(y/n)");
        String in = scanner.next();

        while (!"y".equals(in) && !"n".equals(in)) {
            System.out.print("Invalid input. Please write 'y' or 'n': ");
            in = scanner.next();
        }

        List<Habit> habits;
        User user = App.getCurrentUser();
        if (user.getRole().equals(User.Role.ADMIN)) {
            if ("y".equals(in)) {
                habits = habitRepository.search(getHabitFilters(scanner));
            } else {
                habits = habitRepository.getAll();
            }
        } else {
            if ("y".equals(in)) {
                habits = habitRepository.searchForUser(user.getId(), getHabitFilters(scanner));
            } else {
                habits = habitRepository.getAllForUser(user.getId());
            }
        }

        for (Habit habit : habits) {
            System.out.println(habit);
        }
        InputParser.parseCKey(scanner);
    }

    @Override
    public void create(Scanner scanner) {
        HabitForm form = new HabitForm();
        System.out.print("Enter name: ");
        form.setName(scanner.next());
        if (habitRepository.existsByName(form.getName())) {
            System.out.println("Habit with name " + form.getName() + " already exists. Try again.");
            return;
        }

        System.out.print("Enter description: ");
        form.setDescription(scanner.next());

        System.out.print("Enter frequency(1 - daily, 2 - weekly, 3 - monthly): ");
        form.setFrequency(InputParser.parseFrequency(scanner));

        Habit habit = new Habit(
                IdUtil.generateHabitId(),
                form.getName(),
                form.getDescription(),
                Habit.Frequency.valueOf(form.getFrequency()),
                App.getCurrentUser().getId());
        habitRepository.save(habit);
        System.out.println("Habit created.");
        InputParser.parseCKey(scanner);
    }

    @Override
    public void update(Scanner scanner) {
        System.out.println("Enter name of the habit: ");

        Habit habit = habitRepository.getByName(scanner.next());
        if (habit == null) {
            System.out.println("Habit not found.");
            return;
        }

        System.out.println("Fill out new values for habit.");
        HabitForm form = new HabitForm();
        System.out.print("Enter name: ");
        form.setName(scanner.next());

        System.out.print("Enter description: ");
        form.setDescription(scanner.next());

        System.out.print("Enter frequency(1 - daily, 2 - weekly, 3 - monthly): ");
        form.setFrequency(InputParser.parseFrequency(scanner));

        if (habitRepository.existsByName(form.getName())) {
            System.out.println("Habit with name " + form.getName() + " already exists. Try again.");
            return;
        }

        habit.setName(form.getName());
        habit.setDescription(form.getDescription());
        habit.setFrequency(Habit.Frequency.valueOf(form.getFrequency()));
        habitRepository.update(habit);
        System.out.println("Habit updated.");
        InputParser.parseCKey(scanner);
    }

    @Override
    public void deleteByName(Scanner scanner) {
        System.out.print("Enter habit name: ");

        String name = scanner.next();
        Habit habit = habitRepository.getByName(name);
        if (habit == null) {
            System.out.println("Habit with name " + name + " not found.");
        } else {
            boolean res = habitRepository.delete(App.getCurrentUser().getId(), habit);
            if (res) {
                habitHistoryRepository.deleteByHabitId(habit.getId());
                System.out.println("Habit deleted.");
            } else {
                System.out.println("Something went wrong.");
            }
        }
        InputParser.parseCKey(scanner);
    }

    /**
     * Forms instance of {@link HabitSearchForm} according to user input.
     *
     * @param scanner scanner for reading user input
     * @return created instance of a HabitSearchForm
     */
    private HabitSearchForm getHabitFilters(Scanner scanner) {
        HabitSearchForm form = new HabitSearchForm();
        System.out.print("Enter name: ");
        form.setName(scanner.next());

        System.out.print("Enter frequency(1 - daily, 2 - weekly, 3 - monthly): ");
        form.setFrequency(InputParser.parseFrequency(scanner));

        System.out.print("Enter date from(format yyyy-MM-dd): ");
        form.setFrom(InputParser.parseDate(scanner));

        System.out.print("Enter date to(format yyyy-MM-dd): ");
        form.setTo(InputParser.parseDate(scanner));

        return form;
    }
}
