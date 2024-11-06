package ru.ylab.services.entities.impl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.ylab.dto.in.PeriodForm;
import ru.ylab.dto.out.HabitCompletionPercent;
import ru.ylab.dto.out.HabitCompletionStreak;
import ru.ylab.dto.out.HabitHistoryProjection;
import ru.ylab.models.Habit;
import ru.ylab.models.HabitHistory;
import ru.ylab.repositories.HabitHistoryRepository;
import ru.ylab.services.entities.HabitHistoryService;
import ru.ylab.services.entities.HabitService;

/**
 * Service implementing {@link HabitHistoryService}.
 *
 * @author azatyamanaev
 */
@RequiredArgsConstructor
@Service
public class HabitHistoryServiceImpl implements HabitHistoryService {

    private final HabitHistoryRepository habitHistoryRepository;
    private final HabitService habitService;

    @Override
    public void markHabitCompleted(Long userId, Long habitId, LocalDate completedOn) {
        Habit habit = habitService.getForUser(userId, habitId);

        HabitHistory history = new HabitHistory();
        history.setUserId(userId);
        history.setHabitId(habit.getId());
        history.setCompletedOn(completedOn);
        habitHistoryRepository.save(history);
    }

    @Override
    public HabitHistoryProjection getHabitHistory(Long userId, Long habitId) {
        return habitHistoryRepository.getByHabitId(habitService.getForUser(userId, habitId).getId());
    }

    @Override
    public List<HabitCompletionStreak> habitCompletionStreak(Long userId) {
        List<HabitCompletionStreak> streaks = new ArrayList<>();
        habitService.getHabitsForUser(userId)
                    .forEach(x -> streaks.add(new HabitCompletionStreak(x.getName(), countStreak(x))));
        return streaks;
    }

    @Override
    public List<HabitCompletionPercent> habitCompletionPercent(Long userId, PeriodForm form) {
        List<HabitCompletionPercent> percents = new ArrayList<>();
        habitService.getHabitsForUser(userId)
                    .forEach(x -> percents.add(new HabitCompletionPercent(x.getName(),
                            completionPercent(x, form.getFrom(), form.getTo()), form.getFrom(), form.getTo())));
        return percents;
    }

    @Override
    public List<HabitHistoryProjection> habitCompletionReport(Long userId) {
        List<HabitHistoryProjection> report = new ArrayList<>();
        habitService.getHabitsForUser(userId)
                    .forEach(x -> report.add(getHabitHistory(userId, x.getId())));
        return report;
    }

    /**
     * Counts current habit completion streak from when it was created.
     *
     * @param habit habit data
     * @return streak
     */
    private int countStreak(@NotNull Habit habit) {
        HabitHistoryProjection history = habitHistoryRepository.getByHabitId(habit.getId());
        if (history == null || history.getDays() == null) {
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
        HabitHistoryProjection history = habitHistoryRepository.getByHabitId(habit.getId());
        if (history == null || history.getDays() == null || history.getDays().isEmpty()) {
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
