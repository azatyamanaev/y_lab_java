package ru.ylab.core.dto.out;

import java.time.LocalDate;
import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Habit history projection.
 *
 * @author azatyamanaev
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
