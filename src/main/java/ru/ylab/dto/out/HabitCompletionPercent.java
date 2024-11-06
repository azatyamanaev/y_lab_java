package ru.ylab.dto.out;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Habit completion percent.
 *
 * @param name habit name
 * @param percent habit completion percent
 * @param from date from which percent was counted
 * @param to date until which percent was counted
 * @author azatyamanaev
 */
public record HabitCompletionPercent(

        @Schema(description = "habit name")
        String name,

        @Schema(description = "habit completion percent")
        String percent,

        @Schema(description = "from which date completion was counted")
        LocalDate from,

        @Schema(description = "until which date completion was counted")
        LocalDate to
) { }
