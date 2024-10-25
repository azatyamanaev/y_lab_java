package ru.ylab.forms;

import lombok.Getter;
import lombok.Setter;

/**
 * Class containing filters for searching users.
 *
 * @author azatyamanaev
 */
@Getter
@Setter
public class UserSearchForm {

    /**
     * User name.
     */
    private String name;

    /**
     * User email.
     */
    private String email;

    /**
     * User role.
     */
    private String role;
}
