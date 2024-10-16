package ru.ylab.services.impl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Scanner;

import org.jetbrains.annotations.NotNull;
import ru.ylab.App;
import ru.ylab.models.Habit;
import ru.ylab.models.HabitHistory;
import ru.ylab.repositories.HabitHistoryRepository;
import ru.ylab.repositories.HabitRepository;
import ru.ylab.services.HabitHistoryService;
import ru.ylab.utils.InputParser;

/**
 * Service implementing {@link HabitHistoryService}.
 *
 * @author azatyamanaev
 */
public class HabitHistoryServiceImpl implements HabitHistoryService {

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
     * Creates new HabitHistoryServiceImpl.
     *
     * @param scanner                scanner for reading user input
     * @param habitRepository        HabitRepository instance
     * @param habitHistoryRepository HabitHistoryRepository instance
     */
    public HabitHistoryServiceImpl(Scanner scanner, HabitRepository habitRepository, HabitHistoryRepository habitHistoryRepository) {
        this.scanner = scanner;
        this.habitRepository = habitRepository;
        this.habitHistoryRepository = habitHistoryRepository;
    }

    @Override
    public void markHabitCompleted() {
        System.out.print("Enter habit name: ");
        String name = scanner.next();
        Habit habit = habitRepository.getByName(name);
        if (habit == null) {
            System.out.println("Habit with name " + name + " not found.");
            InputParser.parseCKey(scanner);
            return;
        }

        System.out.print("Enter habit completion date: ");
        LocalDate date = InputParser.parseDate(scanner);

        HabitHistory history = habitHistoryRepository.getByHabitId(habit.getId());
        if (history == null) {
            history = new HabitHistory(
                    App.getCurrentUser().getId(),
                    habit.getId(),
                    new HashSet<>());
        }
        history.getDays().add(date);
        habitHistoryRepository.save(history);
        System.out.println("Habit completion recorded.");
        InputParser.parseCKey(scanner);
    }

    @Override
    public void viewHabitHistory() {
        System.out.print("Enter habit name: ");
        String name = scanner.next();
        Habit habit = habitRepository.getByName(name);
        if (habit == null) {
            System.out.println("Habit with name " + name + " not found.");
            return;
        }

        System.out.print("Enter date from(format yyyy-MM-dd): ");
        LocalDate after = InputParser.parseDate(scanner);

        System.out.print("Enter date until(format yyyy-MM-dd): ");
        LocalDate before = InputParser.parseDate(scanner);

        HabitHistory history = habitHistoryRepository.getByHabitId(habit.getId());
        if (history == null) {
            System.out.println("Habit does not have history.");
        } else {
            System.out.println("After " + after + " before " + before + " habit " + name + " was completed on days:");
            history.getDays().stream()
                   .filter(x -> x.isAfter(after) && x.isBefore(before))
                   .forEach(System.out::println);
        }
        InputParser.parseCKey(scanner);
    }

    @Override
    public void habitCompletionStreak() {
        System.out.println("Current completion streak for habits:");
        habitRepository.getAllForUser(App.getCurrentUser().getId())
                       .forEach(x -> System.out.println("Habit: name - " + x.getName() + ", streak - " + countStreak(x)));
    }

    @Override
    public void habitCompletionPercent() {
        System.out.print("Enter date from: ");
        LocalDate from = InputParser.parseDate(scanner);

        System.out.print("Enter date to: ");
        LocalDate to = InputParser.parseDate(scanner);

        System.out.println("Current habit completion percent:");
        habitRepository.getAllForUser(App.getCurrentUser().getId())
                       .forEach(x -> System.out.println("Habit: name - " + x.getName() + ", completion - " + completionPercent(x, from, to)));
    }

    @Override
    public void habitCompletionReport() {
        System.out.println("Current habit completion: ");
        habitRepository.getAllForUser(App.getCurrentUser().getId())
                       .forEach(x -> {
                           HabitHistory history = habitHistoryRepository.getByHabitId(x.getId());
                           if (history != null) {
                               StringBuilder builder = new StringBuilder();
                               history.getDays().stream()
                                      .sorted(Comparator.naturalOrder())
                                      .forEach(date -> builder.append(date).append(" "));
                               System.out.println("Habit: name - " + x.getName() + ", completed on - " + builder);
                           }
                       });
    }

    /**
     * Counts current habit completion streak from when it was created.
     *
     * @param habit habit data
     * @return streak
     */
    private int countStreak(@NotNull Habit habit) {
        HabitHistory history = habitHistoryRepository.getByHabitId(habit.getId());
        if (history == null) {
            return 0;
        }

        LocalDate created = habit.getCreated();
        LocalDate end = LocalDate.now();
        int streak = 0;
        int days = switch (habit.getFrequency()) {
            case DAILY ->
                    1;
            case WEEKLY ->
                    7;
            case MONTHLY ->
                    30;
        };
        for (LocalDate start = created; start.isBefore(end); start = start.plusDays(days)) {
            if (history.getDays().contains(start)) {
                streak++;
            }
            if (streak > 1 && !history.getDays().contains(start.minusDays(days))) {
                streak = 0;
            }
        }
        return streak;
    }

    /**
     * Calculates completion percent for habit for a certain period.
     *
     * @param habit habit data
     * @param from  from date
     * @param to    to date
     * @return completion percent
     */
    private String completionPercent(@NotNull Habit habit, LocalDate from, LocalDate to) {
        HabitHistory history = habitHistoryRepository.getByHabitId(habit.getId());
        if (history == null) {
            return "0%";
        }

        int count = 0;
        int days = switch (habit.getFrequency()) {
            case DAILY ->
                    1;
            case WEEKLY ->
                    7;
            case MONTHLY ->
                    30;
        };
        int times = (int) from.until(to, ChronoUnit.DAYS) / days;
        for (LocalDate start = from; start.isBefore(to.plusDays(1)); start = start.plusDays(days)) {
            if (history.getDays().contains(start)) {
                count++;
            }
        }

        double value = (double) count / times;
        return String.format("%.2f", value) + "%";
    }
}
