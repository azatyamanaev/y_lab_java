package ru.ylab.services.entities.impl;

import java.util.List;
import java.util.Scanner;

import lombok.extern.slf4j.Slf4j;
import ru.ylab.App;
import ru.ylab.forms.HabitForm;
import ru.ylab.forms.HabitSearchForm;
import ru.ylab.models.Habit;
import ru.ylab.models.User;
import ru.ylab.repositories.HabitHistoryRepository;
import ru.ylab.repositories.HabitRepository;
import ru.ylab.services.entities.HabitService;
import ru.ylab.utils.InputParser;

/**
 * Service implementing {@link HabitService}.
 *
 * @author azatyamanaev
 */
@Slf4j
public class HabitServiceImpl implements HabitService {

    /**
     * Scanner for reading user input.
     */
    private final Scanner scanner;

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
     * @param scanner                scanner for reading user input
     * @param habitRepository        HabitRepository instance
     * @param habitHistoryRepository HabitHistoryRepository instance
     */
    public HabitServiceImpl(Scanner scanner, HabitRepository habitRepository, HabitHistoryRepository habitHistoryRepository) {
        this.scanner = scanner;
        this.habitRepository = habitRepository;
        this.habitHistoryRepository = habitHistoryRepository;
    }

    @Override
    public String getHabits() {
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
                habits = habitRepository.search(getHabitFilters());
            } else {
                habits = habitRepository.getAll();
            }
        } else {
            if ("y".equals(in)) {
                habits = habitRepository.searchForUser(user.getId(), getHabitFilters());
            } else {
                habits = habitRepository.getAllForUser(user.getId());
            }
        }

        StringBuilder response = new StringBuilder();

        for (Habit habit : habits) {
            response.append(habit).append("\n");
        }
        return response.toString();
    }

    @Override
    public void create() {
        HabitForm form = new HabitForm();
        System.out.print("Enter name: ");
        form.setName(scanner.next());
        if (habitRepository.existsByName(form.getName())) {
            log.warn("Habit with name {} already exists.", form.getName());
            return;
        }

        System.out.print("Enter description: ");
        form.setDescription(scanner.next());

        System.out.print("Enter frequency(1 - daily, 2 - weekly, 3 - monthly): ");
        form.setFrequency(InputParser.parseFrequency(scanner));

        Habit habit = new Habit();
        habit.setName(form.getName());
        habit.setDescription(form.getDescription());
        habit.setFrequency(Habit.Frequency.valueOf(form.getFrequency()));
        habit.setUserId(App.getCurrentUser().getId());
        habitRepository.save(habit);
        log.info("Habit created.");
    }

    @Override
    public void update() {
        System.out.println("Enter name of the habit: ");

        Habit habit = habitRepository.getByName(scanner.next());
        if (habit == null) {
            log.warn("Habit not found.");
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
            log.warn("Habit with name {} already exists.", form.getName());
            return;
        }

        habit.setName(form.getName());
        habit.setDescription(form.getDescription());
        habit.setFrequency(Habit.Frequency.valueOf(form.getFrequency()));
        habitRepository.update(habit);
        log.info("Habit updated.");
    }

    @Override
    public void deleteByName() {
        System.out.print("Enter habit name: ");

        String name = scanner.next();
        Habit habit = habitRepository.getByName(name);
        if (habit == null) {
            log.info("Habit with name {} not found.", name);
        } else {
            boolean res = habitRepository.delete(App.getCurrentUser().getId(), habit);
            if (res) {
                log.info("Habit deleted.");
            } else {
                log.warn("Something went wrong.");
            }
        }
    }

    /**
     * Forms instance of {@link HabitSearchForm} according to user input.
     *
     * @return created instance of a HabitSearchForm
     */
    private HabitSearchForm getHabitFilters() {
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
