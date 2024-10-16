package ru.ylab.forms;

import lombok.Data;

/**
 * Class containing data for creating and updating habits.
 *
 * @author azatyamanaev
 */
@Data
public class HabitForm {

    /**
     * Habit name.
     */
    private String name;

    /**
     * Habit description.
     */
    private String description;

    /**
     * How often habit needs to be completed.
     */
    private String frequency;
}
