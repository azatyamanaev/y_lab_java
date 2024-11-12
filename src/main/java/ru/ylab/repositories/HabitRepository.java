package ru.ylab.repositories;

import java.util.List;
import java.util.Optional;

import jakarta.validation.constraints.NotNull;
import ru.ylab.dto.in.HabitSearchForm;
import ru.ylab.models.Habit;

/**
 * Interface describing logic for working with habits storage.
 *
 * @author azatyamanaev
 */
public interface HabitRepository {

    /**
     * Finds habit by id.
     *
     * @param id habit id
     * @return {@code Optional<Habit>}
     */
    Optional<Habit> find(Long id);

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
     * @return whether save is successful
     */
    boolean save(Habit habit);

    /**
     * Updates habit in storage.
     *
     * @param habit instance of Habit to update
     * @return whether update is successful
     */
    boolean update(Habit habit);

    /**
     * Deletes habit and habit history from storage.
     *
     * @param userId id of a user who created habit
     * @param habitId  habit id
     * @return whether deletion is successful
     */
    boolean delete(Long userId, Long habitId);
}
