package ru.ylab.dto.in;

import java.time.LocalDate;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.ylab.models.Habit;

/**
 * Class containing filters for searching habits.
 *
 * @author azatyamanaev
 */
@Getter
@Setter
@EqualsAndHashCode
public class HabitSearchForm {

    /**
     * Habit name.
     */
    private String name;

    /**
     * How often habit needs to be completed.
     */
    private Habit.Frequency frequency;

    /**
     * Habit was created later than date.
     */
    private LocalDate from;

    /**
     * Habit was created before date.
     */
    private LocalDate to;
}
