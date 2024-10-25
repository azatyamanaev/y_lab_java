package ru.ylab.services.entities;

/**
 * Interface describing logic for working with habits.
 *
 * @author azatyamanaev
 */
public interface HabitService {

    /**
     * Gets user habits with filtering depending on user input.
     *
     * @return list of habits in string format
     */
    String getHabits();

    /**
     * Creates habit according to user input.
     */
    void create();

    /**
     * Updates habit according to user input.
     */
    void update();

    /**
     * Deletes habit by name according to user input.
     */
    void deleteByName();
}
