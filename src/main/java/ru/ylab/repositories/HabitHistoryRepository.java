package ru.ylab.repositories;

import org.jetbrains.annotations.NotNull;
import ru.ylab.models.HabitHistory;

/**
 * Interface describing logic for working with habit history storage.
 *
 * @author azatyamanaev
 */
public interface HabitHistoryRepository {

    /**
     * Saves habit history to storage.
     *
     * @param history habit history data
     * @return saved history
     */
    HabitHistory save(HabitHistory history);

    /**
     * Gets habit history by habit id
     *
     * @param habitId habit id
     * @return habit history or null
     */
    HabitHistory getByHabitId(@NotNull Long habitId);

    /**
     * Deletes history of a habit by habit id.
     *
     * @param habitId habit id
     * @return whether deletion is successful
     */
    boolean deleteByHabitId(@NotNull Long habitId);
}
