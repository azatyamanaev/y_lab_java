package ru.ylab.forms;

import java.time.LocalDate;

import lombok.Data;

/**
 * Class containing filters for searching habits.
 *
 * @author azatyamanaev
 */
@Data
public class HabitSearchForm {

    /**
     * Habit name.
     */
    private String name;

    /**
     * How often habit needs to be completed.
     */
    private String frequency;

    /**
     * Habit was created later than date.
     */
    private LocalDate from;

    /**
     * Habit was created before date.
     */
    private LocalDate to;
}
