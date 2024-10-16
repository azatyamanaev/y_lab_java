package ru.ylab.models;

import java.time.LocalDate;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Class storing habit completion history.
 *
 * @author azatyamanaev
 */
@AllArgsConstructor
@Getter
public class HabitHistory {

    /**
     * User id.
     */
    private Long userId;

    /**
     * Habit id.
     */
    private Long habitId;

    /**
     * Days when user completed habit.
     */
    private Set<LocalDate> days;
}
