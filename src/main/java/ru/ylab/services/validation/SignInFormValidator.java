package ru.ylab.services.validation;

import ru.ylab.dto.in.SignInForm;
import ru.ylab.exception.HttpException;

/**
 * Validator for validating {@link SignInForm}.
 *
 * @author azatyamanaev
 */
public class SignInFormValidator implements Validator<SignInForm> {

    @Override
    public void validate(SignInForm data) {
        isEmpty(data);

        HttpException exception = HttpException.validationError();
        validEmail(data.getEmail(), exception);
        isEmptyString(data.getEmail(), "email", exception);
        isEmptyString(data.getPassword(), "password", exception);

        exception.throwIfErrorsNotEmpty();
    }
}
