package ru.ylab.dto.in;

import lombok.Getter;
import lombok.Setter;

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
    private String email;

    /**
     * User password.
     */
    private String password;
}
