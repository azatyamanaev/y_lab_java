package ru.ylab.services.entities;

import java.time.LocalDate;

import ru.ylab.models.HabitHistory;

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
     * @param name habit name
     * @param completedOn completed on day
     */
    void markHabitCompleted(Long userId, String name, LocalDate completedOn);

    /**
     * Gets days on which habit was completed for user.
     *
     * @param userId user id
     * @param name habit name
     * @return habit history
     */
    HabitHistory getHabitHistory(Long userId, String name);

    /**
     * Prints current habit completion streak for user.
     *
     * @param userId user id
     * @return habit completion streak in string format
     */
    String habitCompletionStreak(Long userId);

    /**
     * Prints habit completion percent for user for the period.
     *
     * @param userId user id
     * @return habit completion percent in string format
     */
    String habitCompletionPercent(Long userId);

    /**
     * Prints habit completion report for user.
     *
     * @param userId user id
     * @return habit completion report in string format
     */
    String habitCompletionReport(Long userId);
}
