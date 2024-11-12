package ru.ylab.dto.out;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Habit completion streak.
 *
 * @param name habit name
 * @param streak current streak length
 * @author azatyamanaev
 */
public record HabitCompletionStreak(

        @Schema(description = "habit name")
        String name,

        @Schema(description = "current habit completion streak")
        int streak
) {}
