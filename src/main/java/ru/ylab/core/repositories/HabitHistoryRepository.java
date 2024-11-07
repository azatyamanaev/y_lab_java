package ru.ylab.core.repositories;

import jakarta.validation.constraints.NotNull;
import ru.ylab.core.dto.out.HabitHistoryProjection;
import ru.ylab.core.models.HabitHistory;

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
     * @return whether save is successful
     */
    boolean save(HabitHistory history);

    /**
     * Gets habit history by habit id
     *
     * @param habitId habit id
     * @return habit history projection
     */
    HabitHistoryProjection getByHabitId(@NotNull Long habitId);

    /**
     * Deletes history of a habit by habit id.
     *
     * @param habitId habit id
     * @return whether deletion is successful
     */
    boolean deleteByHabitId(@NotNull Long habitId);
}
