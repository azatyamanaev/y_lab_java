package ru.ylab.services.validation;

import java.util.List;

import ru.ylab.dto.in.UserSearchForm;
import ru.ylab.exception.HttpException;
import ru.ylab.models.User;
import ru.ylab.utils.constants.ErrorConstants;

/**
 * Validator for validating {@link UserSearchForm}.
 *
 * @author azatyamanaev
 */
public class UserSearchFormValidator implements Validator<UserSearchForm> {

    @Override
    public void validate(UserSearchForm data) {
        isEmpty(data);

        HttpException exception = HttpException.validationError();

        if (data.getName() != null && data.getName().isBlank()) {
            exception.addDetail(ErrorConstants.EMPTY_PARAM, "name");
        }
        if (data.getEmail() != null && data.getEmail().isBlank()) {
            exception.addDetail(ErrorConstants.EMPTY_PARAM, "email");
        }
        if (data.getRole() != null && data.getRole().isBlank()) {
            exception.addDetail(ErrorConstants.EMPTY_PARAM, "role");
        } else if (data.getRole() != null
                && !List.of(User.Role.USER.name(), User.Role.ADMIN.name()).contains(data.getRole())) {
            exception.addDetail(ErrorConstants.INVALID_PARAMETER, "role");
        }

        exception.throwIfErrorsNotEmpty();
    }
}
