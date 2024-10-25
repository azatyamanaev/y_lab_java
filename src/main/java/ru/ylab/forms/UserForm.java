package ru.ylab.forms;

import lombok.Getter;
import lombok.Setter;

/**
 * Class containing data for creating and updating users.
 *
 * @author azatyamanaev
 */
@Getter
@Setter
public class UserForm {

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
