package ru.ylab.dto.in;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import ru.ylab.utils.constants.AppConstants;
import ru.ylab.utils.constants.ErrorConstants;

/**
 * Class containing data for signing in.
 *
 * @author azatyamanaev
 */
@Getter
@Setter
public class SignInForm {

    /**
     * User email.
     */
    @NotBlank(message = ErrorConstants.EMPTY_PARAM)
    @Email(regexp = AppConstants.EMAIL_REGEX, message = ErrorConstants.INVALID_PARAM)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "user email")
    private String email;

    /**
     * User password.
     */
    @NotBlank(message = ErrorConstants.EMPTY_PARAM)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "user password")
    private String password;
}
