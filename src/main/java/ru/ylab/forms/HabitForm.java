package ru.ylab.forms;

import lombok.Getter;
import lombok.Setter;

/**
 * Class containing data for creating and updating habits.
 *
 * @author azatyamanaev
 */
@Getter
@Setter
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
