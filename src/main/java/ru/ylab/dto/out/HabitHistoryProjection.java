package ru.ylab.dto.out;

import java.time.LocalDate;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

/**
 * Habit history projection.
 *
 * @author azatyamanaev
 */
@Getter
@Setter
public class HabitHistoryProjection {

    /**
     * Habit name.
     */
    private String habitName;

    /**
     * Days on which habit was completed.
     */
    private Set<LocalDate> days;
}
