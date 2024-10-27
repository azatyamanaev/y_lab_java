package ru.ylab.services.validation;

import ru.ylab.dto.in.SignUpForm;
import ru.ylab.exception.HttpException;
import ru.ylab.repositories.UserRepository;
import ru.ylab.utils.constants.ErrorConstants;

/**
 * Validator for validating {@link SignUpForm}.
 *
 * @author azatyamanaev
 */
public class SignUpFormValidator implements Validator<SignUpForm> {

    /**
     * Instance of an {@link UserRepository}.
     */
    private final UserRepository userRepository;

    /**
     * Creates new SignUpFormValidator.
     *
     * @param userRepository UserRepository instance
     */
    public SignUpFormValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public void validate(SignUpForm data) {
        isEmpty(data);

        HttpException exception = HttpException.validationError();
        isEmptyString(data.getName(), "name", exception);
        if (validEmail(data.getEmail(), exception) && !isEmptyString(data.getEmail(), "email", exception)
                && userRepository.existsByEmail(data.getEmail())) {
            exception.addDetail(ErrorConstants.ALREADY_EXISTS, "email");
        }
        isEmptyString(data.getPassword(), "password", exception);

        exception.throwIfErrorsNotEmpty();
    }
}
