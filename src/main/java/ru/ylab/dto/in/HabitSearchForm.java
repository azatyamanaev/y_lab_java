package ru.ylab.dto.in;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springdoc.core.annotations.ParameterObject;
import ru.ylab.models.Habit;

/**
 * Class containing filters for searching habits.
 *
 * @author azatyamanaev
 */
@Getter
@Setter
@ParameterObject
@EqualsAndHashCode
public class HabitSearchForm {

    /**
     * Habit name.
     */
    @Parameter
    private String name;

    /**
     * How often habit needs to be completed.
     */
    @Parameter(description = "how often habit needs to be completed")
    private Habit.Frequency frequency;

    /**
     * Habit was created later than date.
     */
    @Parameter
    private LocalDate from;

    /**
     * Habit was created before date.
     */
    @Parameter
    private LocalDate to;
}
