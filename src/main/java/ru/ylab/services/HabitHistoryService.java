package ru.ylab.services;

/**
 * Interface describing logic for working with habit history.
 *
 * @author azatyamanaev
 */
public interface HabitHistoryService {

    /**
     * Mark habit as completed on day from user input.
     */
    void markHabitCompleted();

    /**
     * View days on which habit was completed for user.
     */
    void viewHabitHistory();

    /**
     * Prints current habit completion streak for user.
     */
    void habitCompletionStreak();

    /**
     * Prints habit completion percent for user for the period.
     */
    void habitCompletionPercent();

    /**
     * Prints habit completion report for user
     */
    void habitCompletionReport();
}
