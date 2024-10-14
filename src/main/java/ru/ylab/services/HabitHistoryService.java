package ru.ylab.services;

import java.util.Scanner;

/**
 * Interface describing logic for working with habit history.
 *
 * @author azatyamanaev
 */
public interface HabitHistoryService {

    /**
     * Mark habit as completed on day from user input.
     *
     * @param scanner scanner for reading user input
     */
    void markHabitCompleted(Scanner scanner);

    /**
     * View days on which habit was completed for user.
     *
     * @param scanner scanner for reading user input
     */
    void viewHabitHistory(Scanner scanner);

    /**
     * Prints current habit completion streak for user.
     */
    void habitCompletionStreak();

    /**
     * Prints habit completion percent for user for the period.
     *
     * @param scanner scanner for reading user input
     */
    void habitCompletionPercent(Scanner scanner);

    /**
     * Prints habit completion report for user
     */
    void habitCompletionReport();
}
