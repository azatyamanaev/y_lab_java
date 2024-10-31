package ru.ylab.services.validation;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.ylab.dto.in.SignUpForm;
import ru.ylab.repositories.UserRepository;
import ru.ylab.utils.constants.ErrorConstants;

/**
 * Validator for validating {@link SignUpForm}.
 *
 * @author azatyamanaev
 */
@RequiredArgsConstructor
@Component("signUpFormValidator")
public class SignUpFormValidator implements Validator, DtoValidator {

    private final UserRepository userRepository;

    @Override
    public boolean supports(@NotNull Class<?> clazz) {
        return SignUpForm.class.equals(clazz);
    }

    @Override
    public void validate(@NotNull Object target, @NotNull Errors errors) {
        SignUpForm form = (SignUpForm) target;

        if (isEmptyString(form.getEmail())) {
            errors.rejectValue("email", ErrorConstants.EMPTY_PARAM);
        } else if (!validEmail(form.getEmail())) {
            errors.rejectValue("email", ErrorConstants.INVALID_PARAMETER);
        } else if (userRepository.existsByEmail(form.getEmail())) {
            errors.rejectValue("email", ErrorConstants.ALREADY_EXISTS);
        }

        if (isEmptyString(form.getName())) {
            errors.rejectValue("name", ErrorConstants.EMPTY_PARAM);
        }

        if (isEmptyString(form.getPassword())) {
            errors.rejectValue("password", ErrorConstants.EMPTY_PARAM);
        }
    }
}
