package ru.ylab.dto.out;

/**
 * Habit completion streak.
 *
 * @param name habit name
 * @param streak current streak length
 * @author azatyamanaev
 */
public record HabitCompletionStreak(String name, int streak) {}
