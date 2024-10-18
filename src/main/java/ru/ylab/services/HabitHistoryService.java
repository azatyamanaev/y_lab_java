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
     *
     * @return habit history in string format
     */
    String viewHabitHistory();

    /**
     * Prints current habit completion streak for user.
     *
     * @return habit completion streak in string format
     */
    String habitCompletionStreak();

    /**
     * Prints habit completion percent for user for the period.
     *
     * @return habit completion percent in string format
     */
    String habitCompletionPercent();

    /**
     * Prints habit completion report for user
     *
     * @return habit completion report in string format
     */
    String habitCompletionReport();
}
