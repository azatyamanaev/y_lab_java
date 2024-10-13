package ru.ylab.repositories;

import org.jetbrains.annotations.NotNull;
import ru.ylab.models.HabitHistory;

/**
 * Class for working with habit history storage.
 *
 * @author azatyamanaev
 */
public class HabitHistoryRepository {

    /**
     * Instance of a {@link Storage}
     */
    private final Storage storage;

    /**
     * Creates new HabitHistoryRepository.
     *
     * @param storage Storage instance
     */
    public HabitHistoryRepository(Storage storage) {
        this.storage = storage;
    }

    /**
     * Saves habit history to storage.
     *
     * @param history habit history data
     * @return saved history
     */
    public HabitHistory save(HabitHistory history) {
        storage.getHabitHistory().put(history.getHabitId(), history);
        return history;
    }

    /**
     * Gets habit history by habit id
     *
     * @param habitId habit id
     * @return habit history or null
     */
    public HabitHistory getByHabitId(@NotNull Long habitId) {
        return storage.getHabitHistory().get(habitId);
    }

    /**
     * Deletes history of a habit by habit id.
     *
     * @param habitId habit id
     * @return whether deletion is successful
     */
    public boolean deleteByHabitId(@NotNull Long habitId) {
        return storage.getHabitHistory().remove(habitId) != null;
    }
}
