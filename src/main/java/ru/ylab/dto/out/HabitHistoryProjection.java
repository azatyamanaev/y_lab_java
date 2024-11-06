package ru.ylab.dto.out;

import java.time.LocalDate;
import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * Habit history projection.
 *
 * @author azatyamanaev
 */
@Getter
@Setter
public class HabitHistoryProjection {

    /**
     * Habit name.
     */
    @Schema(description = "habit name")
    private String habitName;

    /**
     * Days on which habit was completed.
     */
    @Schema(description = "days on which habit was completed")
    private Set<LocalDate> days;
}
