package ru.ylab.models;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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

    /**
     * Days when user completed habit.
     */
    private Set<LocalDate> days = new HashSet<>();

    public HabitHistory(Long userId, Long habitId, LocalDate completedOn) {
        this.userId = userId;
        this.habitId = habitId;
        this.completedOn = completedOn;
    }
}
