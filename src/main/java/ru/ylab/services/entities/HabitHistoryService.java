package ru.ylab.services.entities;

import java.time.LocalDate;
import java.util.List;

import ru.ylab.dto.in.PeriodForm;
import ru.ylab.dto.out.HabitCompletionPercent;
import ru.ylab.dto.out.HabitCompletionStreak;
import ru.ylab.dto.out.HabitHistoryProjection;

/**
 * Interface describing logic for working with habit history.
 *
 * @author azatyamanaev
 */
public interface HabitHistoryService {

    /**
     * Mark habit as completed.
     *
     * @param userId user id
     * @param habitId habit id
     * @param completedOn completed on day
     */
    void markHabitCompleted(Long userId, Long habitId, LocalDate completedOn);

    /**
     * Gets days on which habit was completed for user.
     *
     * @param userId user id
     * @param habitId habit id
     * @return habit history
     */
    HabitHistoryProjection getHabitHistory(Long userId, Long habitId);

    /**
     * Gets current habit completion streak for user.
     *
     * @param userId user id
     * @return habit completion streak list
     */
    List<HabitCompletionStreak> habitCompletionStreak(Long userId);

    /**
     * Gets habit completion percent for user for the period.
     *
     * @param userId user id
     * @param form period for which to display completion percent
     * @return habit completion percent list
     */
    List<HabitCompletionPercent> habitCompletionPercent(Long userId, PeriodForm form);

    /**
     * Gets habit completion report for user.
     *
     * @param userId user id
     * @return habit completion report
     */
    List<HabitHistoryProjection> habitCompletionReport(Long userId);
}
