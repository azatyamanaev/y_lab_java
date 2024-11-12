package ru.ylab.dto.in;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import ru.ylab.models.User;
import ru.ylab.utils.constants.ErrorConstants;

/**
 * Class extending {@link SignUpForm} and adding role field.
 *
 * @author azatyamanaev
 */
@Getter
@Setter
public class UserForm extends SignUpForm {

    /**
     * User role.
     */
    @NotNull(message = ErrorConstants.EMPTY_PARAM)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "user role")
    private User.Role role;
}
