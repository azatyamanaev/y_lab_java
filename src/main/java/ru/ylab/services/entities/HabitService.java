package ru.ylab.services.entities;

import java.util.List;

import ru.ylab.dto.in.HabitForm;
import ru.ylab.dto.in.HabitSearchForm;
import ru.ylab.models.Habit;

/**
 * Interface describing logic for working with habits.
 *
 * @author azatyamanaev
 */
public interface HabitService {

    /**
     * Gets all habits for user.
     *
     * @param userId user id
     * @return list of habits in string format
     */
    List<Habit> getHabitsForUser(Long userId);

    /**
     * Searches habits with specified filters.
     *
     * @param userId user id
     * @param form filters to apply
     * @return list of habits
     */
    List<Habit> searchHabitsForUser(Long userId, HabitSearchForm form);

    /**
     * Creates habit.
     *
     * @param userId user id
     * @param form habit data
     * @return created habit
     */
    Habit create(Long userId, HabitForm form);

    /**
     * Updates habit.
     *
     * @param name habit name
     * @param form habit data
     * @return updated habit
     */
    Habit update(String name, HabitForm form);

    /**
     * Deletes habit by name.
     *
     * @param userId habit author id
     * @param name habit name
     * @return whether deletion is successful
     */
    boolean deleteByName(Long userId, String name);
}
