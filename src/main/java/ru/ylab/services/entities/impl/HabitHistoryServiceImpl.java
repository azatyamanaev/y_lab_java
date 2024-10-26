package ru.ylab.services.entities.impl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import ru.ylab.models.Habit;
import ru.ylab.models.HabitHistory;
import ru.ylab.repositories.HabitHistoryRepository;
import ru.ylab.repositories.HabitRepository;
import ru.ylab.services.entities.HabitHistoryService;

/**
 * Service implementing {@link HabitHistoryService}.
 *
 * @author azatyamanaev
 */
@Slf4j
public class HabitHistoryServiceImpl implements HabitHistoryService {

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
     * @param habitRepository        HabitRepository instance
     * @param habitHistoryRepository HabitHistoryRepository instance
     */
    public HabitHistoryServiceImpl(HabitRepository habitRepository, HabitHistoryRepository habitHistoryRepository) {
        this.habitRepository = habitRepository;
        this.habitHistoryRepository = habitHistoryRepository;
    }

    @Override
    public void markHabitCompleted(Long userId, String name, LocalDate completedOn) {
        Habit habit = habitRepository.getByName("name");
        if (habit == null) {
            log.warn("Habit with name {} not found.", "name");
            return;
        }

        HabitHistory history = habitHistoryRepository.getByHabitId(habit.getId());
        if (history == null) {
            history = new HabitHistory();
            history.setUserId(userId);
            history.setHabitId(habit.getId());
        }
        history.setCompletedOn(completedOn);
        habitHistoryRepository.save(history);
        log.info("Habit completion recorded.");
    }

    @Override
    public HabitHistory getHabitHistory(Long userId, String name) {
        return habitHistoryRepository.getByHabitId(habitRepository.getByName(name).getId());
    }

    @Override
    public String habitCompletionStreak(Long userId) {
        StringBuilder response = new StringBuilder("Current completion streak for habits:\n");
        habitRepository.getAllForUser(userId)
                       .forEach(x -> response.append("Habit: name - ")
                                             .append(x.getName())
                                             .append(", streak - ")
                                             .append(countStreak(x))
                                             .append("\n"));
        return response.toString();
    }

    @Override
    public String habitCompletionPercent(Long userId) {
        return "Habit completion percent";
    }

    @Override
    public String habitCompletionReport(Long userId) {
        StringBuilder response = new StringBuilder("Current habit completion:\n");
        habitRepository.getAllForUser(userId)
                       .forEach(x -> {
                           HabitHistory history = habitHistoryRepository.getByHabitId(x.getId());
                           if (history != null) {
                               StringBuilder builder = new StringBuilder();
                               history.getDays().stream()
                                      .sorted(Comparator.naturalOrder())
                                      .forEach(date -> builder.append(date).append(" "));

                               response.append("Habit: name - ")
                                       .append(x.getName())
                                       .append(", completed on - ")
                                       .append(builder)
                                       .append("\n");
                           }
                       });
        return response.toString();
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
