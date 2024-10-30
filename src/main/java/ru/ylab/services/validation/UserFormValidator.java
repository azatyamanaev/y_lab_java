package ru.ylab.services.validation;

import java.util.List;

import ru.ylab.dto.in.SignUpForm;
import ru.ylab.dto.in.UserForm;
import ru.ylab.exception.HttpException;
import ru.ylab.models.User;
import ru.ylab.repositories.UserRepository;
import ru.ylab.utils.constants.ErrorConstants;

/**
 * Validator for validating {@link UserForm}.
 *
 * @author azatyamanaev
 */
public class UserFormValidator extends SignUpFormValidator {

    /**
     * Creates new SignUpFormValidator.
     *
     * @param userRepository UserRepository instance
     */
    public UserFormValidator(UserRepository userRepository) {
        super(userRepository);
    }

    @Override
    public void validate(SignUpForm data) {
        super.validate(data);
        if (data instanceof UserForm) {
            UserForm userForm = (UserForm) data;
            HttpException exception = HttpException.validationError();
            if (!isEmptyString(userForm.getRole(), "role", exception)
                    && !List.of(User.Role.USER.name(), User.Role.ADMIN.name()).contains(userForm.getRole())) {
                exception.addDetail(ErrorConstants.INVALID_PARAMETER, "role");
            }

            exception.throwIfErrorsNotEmpty();
        }
    }
}
