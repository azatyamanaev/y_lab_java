package ru.ylab.core.models;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class storing habit completion history.
 *
 * @author azatyamanaev
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
}
