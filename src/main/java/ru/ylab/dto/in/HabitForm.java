package ru.ylab.dto.in;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import ru.ylab.models.Habit;
import ru.ylab.utils.constants.ErrorConstants;

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
    @NotBlank(message = ErrorConstants.EMPTY_PARAM)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "habit name")
    private String name;

    /**
     * Habit description.
     */
    @NotBlank(message = ErrorConstants.EMPTY_PARAM)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "habit description")
    private String description;

    /**
     * How often habit needs to be completed.
     */
    @NotNull(message = ErrorConstants.EMPTY_PARAM)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "how often habit needs to be completed")
    private Habit.Frequency frequency;
}
