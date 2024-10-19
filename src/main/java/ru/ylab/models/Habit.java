package ru.ylab.models;

import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class representing habit.
 *
 * @author azatyamanaev
 */
@Data
@NoArgsConstructor
public class Habit {

    /**
     * Habit id.
     */
    private Long id;

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
    private Frequency frequency;

    /**
     * When habit was created.
     */
    private LocalDate created;

    /**
     * Id of a user who created habit.
     */
    private Long userId;

    /**
     * Creates a new habit with specified id, name, description, frequency, userId.
     * Field {@link Habit#created} is set to {@code LocalDate.now()}.
     *
     * @param id          habit id
     * @param name        habit name
     * @param description habit description
     * @param frequency   habit frequency
     * @param userId      id of a user who created habit
     */
    public Habit(Long id, String name, String description, Frequency frequency, Long userId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.frequency = frequency;
        this.created = LocalDate.now();
        this.userId = userId;
    }

    /**
     * Translates Habit instance to string.
     *
     * @return Habit instance in string format
     */
    @Override
    public String toString() {
        return "Habit{ id: " + id + ", name: " + name + ", description: " +
                description + ", frequency: " + frequency + ", created: " + created + " }";
    }

    /**
     * Enum for habit frequency.
     */
    public enum Frequency {

        /**
         * For habits that need to be completed every day.
         */
        DAILY,

        /**
         * For habits that need to be completed every week.
         */
        WEEKLY,

        /**
         * For habits that need to be completed every month.
         */
        MONTHLY
    }
}
