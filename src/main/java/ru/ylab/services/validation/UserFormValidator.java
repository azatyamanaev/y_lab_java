package ru.ylab.services.validation;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ru.ylab.dto.in.SignUpForm;
import ru.ylab.dto.in.UserForm;
import ru.ylab.repositories.UserRepository;
import ru.ylab.utils.constants.ErrorConstants;

/**
 * Validator for validating {@link UserForm}.
 *
 * @author azatyamanaev
 */
@Component("userFormValidator")
public class UserFormValidator extends SignUpFormValidator {

    public UserFormValidator(UserRepository userRepository) {
        super(userRepository);
    }

    @Override
    public boolean supports(@NotNull Class<?> clazz) {
        return SignUpForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NotNull Object target, @NotNull Errors errors) {
       super.validate(target, errors);
       if (target instanceof UserForm userForm) {
           if (userForm.getRole() == null) {
               errors.rejectValue("role", ErrorConstants.EMPTY_PARAM);
           }
       }
    }
}
