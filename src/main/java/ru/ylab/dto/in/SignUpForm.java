package ru.ylab.dto.in;

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
    private String name;

    /**
     * User email.
     */
    private String email;

    /**
     * User password.
     */
    private String password;
}
