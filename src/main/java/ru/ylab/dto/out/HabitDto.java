package ru.ylab.dto.out;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.ylab.models.Habit;

/**
 * Habit DTO for sending in server responses.
 *
 * @param id habit id
 * @param name habit name
 * @param description habit description
 * @param frequency how often habit needs to be completed
 * @param created date, when habit was created
 * @author azatyamanaev
 */
public record HabitDto(

        @Schema(description = "habit id")
        Long id,

        @Schema(description = "habit name")
        String name,

        @Schema(description = "habit description")
        String description,

        @Schema(description = "how often habit needs to be completed")
        Habit.Frequency frequency,

        @Schema(description = "when habit was created")
        LocalDate created
) {}
