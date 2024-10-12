package ru.ylab.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import ru.ylab.forms.HabitSearchForm;
import ru.ylab.models.Habit;

/**
 * Class for working with habits storage.
 *
 * @author azatyamanaev
 */
public class HabitRepository {

    /**
     * Map for storing habits data.
     */
    @Getter
    private static final Map<Long, Habit> habits = new HashMap<>();

    /**
     * Finds habit by name.
     *
     * @param name habit name
     * @return {@code Optional<Habit>}
     */
    public Optional<Habit> findByName(String name) {
        return habits.values().stream()
                     .filter(x -> x.getName().equals(name))
                     .findAny();
    }

    /**
     * Gets habit by name.
     *
     * @param name habit name
     * @return habit or null
     */
    public Habit getByName(String name) {
        return findByName(name).orElse(null);
    }

    /**
     * Checks whether habit exists by name.
     *
     * @param name habit name
     * @return whether habit exists
     */
    public boolean existsByName(String name) {
        return findByName(name).isPresent();
    }

    /**
     * Gets all habits.
     *
     * @return list of all habits
     */
    public List<Habit> getAll() {
        return new ArrayList<>(habits.values());
    }

    /**
     * Searches habits with specified filters.
     *
     * @param form filters to apply
     * @return list of habits
     */
    public List<Habit> search(@NotNull HabitSearchForm form) {
        return habits.values().stream()
                     .filter(getPredicates(form))
                     .collect(Collectors.toList());
    }

    /**
     * Gets all habits created by user.
     *
     * @param userId user id
     * @return list of all habits
     */
    public List<Habit> getAllForUser(@NotNull Long userId) {
        return habits.values().stream()
                     .filter(x -> x.getUserId().equals(userId))
                     .collect(Collectors.toList());
    }

    /**
     * Searches habits with specified filters created by user.
     *
     * @param userId user id
     * @param form   filters to apply
     * @return list of habits
     */
    public List<Habit> searchForUser(@NotNull Long userId, @NotNull HabitSearchForm form) {
        return habits.values().stream()
                     .filter(x -> x.getUserId().equals(userId))
                     .filter(getPredicates(form))
                     .collect(Collectors.toList());
    }

    /**
     * Saves habit to storage.
     *
     * @param habit instance of Habit to save
     * @return saved habit
     */
    public Habit save(Habit habit) {
        habits.put(habit.getId(), habit);
        return habit;
    }

    /**
     * Updates habit in storage.
     *
     * @param habit instance of Habit to update
     * @return updated habit
     */
    public Habit update(Habit habit) {
        habits.put(habit.getId(), habit);
        return habit;
    }

    /**
     * Deletes habit from storage.
     *
     * @param userId id of a user who created habit
     * @param habit  habit data
     * @return whether deletion is successful
     */
    public boolean delete(Long userId, @NotNull Habit habit) {
        if (habit.getUserId().equals(userId)) {
            habits.remove(habit.getId());
            return true;
        } else {
            return false;
        }
    }

    /**
     * Forms predicate according to passed HabitSearchForm.
     *
     * @param form form to create predicate from
     * @return created instance of a Predicate
     */
    private Predicate<Habit> getPredicates(@NotNull HabitSearchForm form) {
        Predicate<Habit> predicate = (x) -> true;

        if (form.getName() != null && !form.getName().isBlank()) {
            predicate = predicate.and(x -> x.getName().startsWith(form.getName()));
        }

        if (form.getFrequency() != null) {
            predicate = predicate.and(x -> x.getFrequency().equals(Habit.Frequency.valueOf(form.getFrequency())));
        }

        if (form.getFrom() != null) {
            predicate = predicate.and(x -> x.getCreated().isAfter(form.getFrom().minusDays(1)));
        }

        if (form.getTo() != null) {
            predicate = predicate.and(x -> x.getCreated().isBefore(form.getTo().plusDays(1)));
        }

        return predicate;
    }
}
