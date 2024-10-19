package ru.ylab.models;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class storing habit completion history.
 *
 * @author azatyamanaev
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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
     * Day when user completed habit.
     */
    private LocalDate completedOn;

    /**
     * Days when user completed habit.
     */
    private Set<LocalDate> days = new HashSet<>();
}
