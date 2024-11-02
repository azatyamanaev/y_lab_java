package ru.ylab.dto.in;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import ru.ylab.models.Habit;

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
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "habit name")
    private String name;

    /**
     * Habit description.
     */
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "habit description")
    private String description;

    /**
     * How often habit needs to be completed.
     */
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "how often habit needs to be completed")
    private Habit.Frequency frequency;
}
