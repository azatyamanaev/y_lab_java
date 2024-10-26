package ru.ylab.dto.out;

import java.time.LocalDate;

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
public record HabitDto(Long id, String name, String description, Habit.Frequency frequency, LocalDate created) {}
