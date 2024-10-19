package ru.ylab.repositories;

import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.NotNull;
import ru.ylab.forms.HabitSearchForm;
import ru.ylab.models.Habit;

/**
 * Interface describing logic for working with habits storage.
 *
 * @author azatyamanaev
 */
public interface HabitRepository {

    /**
     * Finds habit by name.
     *
     * @param name habit name
     * @return {@code Optional<Habit>}
     */
    Optional<Habit> findByName(String name);

    /**
     * Gets habit by name.
     *
     * @param name habit name
     * @return habit or null
     */
    Habit getByName(String name);

    /**
     * Checks whether habit exists by name.
     *
     * @param name habit name
     * @return whether habit exists
     */
    boolean existsByName(String name);

    /**
     * Gets all habits.
     *
     * @return list of all habits
     */
    List<Habit> getAll();

    /**
     * Searches habits with specified filters.
     *
     * @param form filters to apply
     * @return list of habits
     */
    List<Habit> search(@NotNull HabitSearchForm form);

    /**
     * Gets all habits created by user.
     *
     * @param userId user id
     * @return list of all habits
     */
    List<Habit> getAllForUser(@NotNull Long userId);

    /**
     * Searches habits with specified filters created by user.
     *
     * @param userId user id
     * @param form   filters to apply
     * @return list of habits
     */
    List<Habit> searchForUser(@NotNull Long userId, @NotNull HabitSearchForm form);

    /**
     * Saves habit to storage.
     *
     * @param habit instance of Habit to save
     * @return saved habit
     */
    Habit save(Habit habit);

    /**
     * Updates habit in storage.
     *
     * @param habit instance of Habit to update
     * @return updated habit
     */
    Habit update(Habit habit);

    /**
     * Deletes habit and habit history from storage.
     *
     * @param userId id of a user who created habit
     * @param habit  habit data
     * @return whether deletion is successful
     */
    boolean delete(Long userId, @NotNull Habit habit);
}
