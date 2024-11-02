package ru.ylab.dto.in;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import ru.ylab.models.User;

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
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "user role")
    private User.Role role;
}
