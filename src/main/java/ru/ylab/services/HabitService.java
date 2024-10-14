package ru.ylab.services;

import java.util.Scanner;

/**
 * Interface describing logic for working with habits.
 *
 * @author azatyamanaev
 */
public interface HabitService {

    /**
     * Gets user habits with filtering depending on user input.
     *
     * @param scanner scanner for reading user input
     */
    void getHabits(Scanner scanner);

    /**
     * Creates habit according to user input.
     *
     * @param scanner scanner for reading user input
     */
    void create(Scanner scanner);

    /**
     * Updates habit according to user input.
     *
     * @param scanner scanner for reading user input
     */
    void update(Scanner scanner);

    /**
     * Deletes habit by name according to user input.
     *
     * @param scanner scanner for reading user input
     */
    void deleteByName(Scanner scanner);
}
