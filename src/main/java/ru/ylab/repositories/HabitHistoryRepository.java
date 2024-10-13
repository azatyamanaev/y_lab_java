package ru.ylab.repositories;

import java.util.HashMap;
import java.util.Map;

import org.jetbrains.annotations.NotNull;
import ru.ylab.models.HabitHistory;

/**
 * Class for working with habit history storage.
 *
 * @author azatyamanaev
 */
public class HabitHistoryRepository {

    /**
     * Map for storing habit history data.
     */
    private static final Map<Long, HabitHistory> habitHistory = new HashMap<>();

    /**
     * Saves habit history to storage.
     *
     * @param history habit history data
     * @return saved history
     */
    public HabitHistory save(HabitHistory history) {
        habitHistory.put(history.getHabitId(), history);
        return history;
    }

    /**
     * Gets habit history by habit id
     *
     * @param habitId habit id
     * @return habit history or null
     */
    public HabitHistory getByHabitId(@NotNull Long habitId) {
        return habitHistory.get(habitId);
    }

    /**
     * Deletes history of a habit by habit id.
     *
     * @param habitId habit id
     * @return whether deletion is successful
     */
    public boolean deleteByHabitId(@NotNull Long habitId) {
        return habitHistory.remove(habitId) != null;
    }
}
