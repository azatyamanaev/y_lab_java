package ru.ylab.dto.in;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * Class containing data for creating and updating users.
 *
 * @author azatyamanaev
 */
@Getter
@Setter
public class SignUpForm {

    /**
     * User name.
     */
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "user name")
    private String name;

    /**
     * User email.
     */
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "user email")
    private String email;

    /**
     * User password.
     */
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "user password")
    private String password;
}
