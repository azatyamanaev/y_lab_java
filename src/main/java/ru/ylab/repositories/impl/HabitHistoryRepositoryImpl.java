package ru.ylab.repositories.impl;

import org.jetbrains.annotations.NotNull;
import ru.ylab.models.HabitHistory;
import ru.ylab.repositories.HabitHistoryRepository;
import ru.ylab.repositories.Storage;

/**
 * Class implementing {@link HabitHistoryRepository}.
 *
 * @author azatyamanaev
 */
public class HabitHistoryRepositoryImpl implements HabitHistoryRepository {

    /**
     * Instance of a {@link Storage}
     */
    private final Storage storage;

    /**
     * Creates new HabitHistoryRepository.
     *
     * @param storage Storage instance
     */
    public HabitHistoryRepositoryImpl(Storage storage) {
        this.storage = storage;
    }

    @Override
    public HabitHistory save(HabitHistory history) {
        storage.getHabitHistory().put(history.getHabitId(), history);
        return history;
    }

    @Override
    public HabitHistory getByHabitId(@NotNull Long habitId) {
        return storage.getHabitHistory().get(habitId);
    }

    @Override
    public boolean deleteByHabitId(@NotNull Long habitId) {
        return storage.getHabitHistory().remove(habitId) != null;
    }
}
