package ru.ylab.forms;

import lombok.Data;

/**
 * Class containing data for creating and updating users.
 *
 * @author azatyamanaev
 */
@Data
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
