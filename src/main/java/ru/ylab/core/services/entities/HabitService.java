package ru.ylab.core.services.entities;

import java.util.List;

import ru.ylab.core.dto.in.HabitForm;
import ru.ylab.core.dto.in.HabitSearchForm;
import ru.ylab.core.exception.HttpException;
import ru.ylab.core.models.Habit;

/**
 * Interface describing logic for working with habits.
 *
 * @author azatyamanaev
 */
public interface HabitService {

    /**
     * Gets habit by id.
     *
     * @param id habit id
     * @return habit
     * @throws HttpException if habit does not exist
     */
    Habit get(Long id);

    /**
     * Gets habit for user.
     *
     * @param userId habit author id
     * @param habitId habit id
     * @return habit
     */
    Habit getForUser(Long userId, Long habitId);

    /**
     * Gets all habits.
     *
     * @return list of habits
     */
    List<Habit> getAll();

    /**
     * Search habits with filters.
     *
     * @param form filters to apply
     * @return list of found habits
     */
    List<Habit> search(HabitSearchForm form);

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
     */
    void create(Long userId, HabitForm form);

    /**
     * Updates habit.
     *
     * @param userId habit author id
     * @param habitId habit id
     * @param form habit data
     */
    void updateForUser(Long userId, Long habitId, HabitForm form);

    /**
     * Deletes habit by id.
     *
     * @param userId habit author id
     * @param habitId habit id
     */
    void deleteForUser(Long userId, Long habitId);
}
