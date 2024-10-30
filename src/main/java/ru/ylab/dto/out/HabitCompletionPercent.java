package ru.ylab.dto.out;

import java.time.LocalDate;

/**
 * Habit completion percent.
 *
 * @param name habit name
 * @param percent habit completion percent
 * @param from date from which percent was counted
 * @param to date until which percent was counted
 * @author azatyamanaev
 */
public record HabitCompletionPercent(String name, String percent, LocalDate from, LocalDate to) {}
