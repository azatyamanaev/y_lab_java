package ru.ylab.forms;

import lombok.Data;

/**
 * Class containing data for signing in.
 *
 * @author azatyamanaev
 */
@Data
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
