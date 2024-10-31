package ru.ylab.dto.in;

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
    private User.Role role;
}
