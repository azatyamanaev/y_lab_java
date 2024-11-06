package ru.ylab.services.validation;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.ylab.dto.in.SignInForm;
import ru.ylab.utils.constants.ErrorConstants;

/**
 * Validator for validating {@link SignInForm}.
 *
 * @author azatyamanaev
 */
@Component
public class SignInFormValidator implements Validator, DtoValidator {

    @Override
    public boolean supports(@NotNull Class<?> clazz) {
        return SignInForm.class.equals(clazz);
    }

    @Override
    public void validate(@NotNull Object target, @NotNull Errors errors) {
        SignInForm form = (SignInForm) target;

        if (isEmptyString(form.getEmail())) {
            errors.rejectValue("email", ErrorConstants.EMPTY_PARAM);
        } else if (!validEmail(form.getEmail())) {
            errors.rejectValue("email", ErrorConstants.INVALID_PARAMETER);
        }

        if (isEmptyString(form.getPassword())) {
            errors.rejectValue("password", ErrorConstants.EMPTY_PARAM);
        }
    }
}
