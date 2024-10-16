package ru.ylab.repositories.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;
import ru.ylab.forms.HabitSearchForm;
import ru.ylab.models.Habit;
import ru.ylab.repositories.HabitRepository;
import ru.ylab.repositories.Storage;

/**
 * Class implementing {@link HabitRepository}.
 *
 * @author azatyamanaev
 */
public class HabitRepositoryImpl implements HabitRepository {

    /**
     * Instance of a {@link Storage}
     */
    private final Storage storage;

    /**
     * Creates new HabitRepositoryImpl.
     *
     * @param storage Storage instance
     */
    public HabitRepositoryImpl(Storage storage) {
        this.storage = storage;
    }

    @Override
    public Optional<Habit> findByName(String name) {
        return storage.getHabits().values().stream()
                      .filter(x -> x.getName().equals(name))
                      .findAny();
    }

    @Override
    public Habit getByName(String name) {
        return findByName(name).orElse(null);
    }

    @Override
    public boolean existsByName(String name) {
        return findByName(name).isPresent();
    }

    @Override
    public List<Habit> getAll() {
        return new ArrayList<>(storage.getHabits().values());
    }

    @Override
    public List<Habit> search(@NotNull HabitSearchForm form) {
        return storage.getHabits().values().stream()
                      .filter(getPredicates(form))
                      .collect(Collectors.toList());
    }

    @Override
    public List<Habit> getAllForUser(@NotNull Long userId) {
        return storage.getHabits().values().stream()
                      .filter(x -> x.getUserId().equals(userId))
                      .collect(Collectors.toList());
    }

    @Override
    public List<Habit> searchForUser(@NotNull Long userId, @NotNull HabitSearchForm form) {
        return storage.getHabits().values().stream()
                      .filter(x -> x.getUserId().equals(userId))
                      .filter(getPredicates(form))
                      .collect(Collectors.toList());
    }

   @Override
    public Habit save(Habit habit) {
        storage.getHabits().put(habit.getId(), habit);
        return habit;
    }

    @Override
    public Habit update(Habit habit) {
        storage.getHabits().put(habit.getId(), habit);
        return habit;
    }

    @Override
    public boolean delete(Long userId, @NotNull Habit habit) {
        if (habit.getUserId().equals(userId)) {
            return storage.getHabits().remove(habit.getId()) != null;
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
