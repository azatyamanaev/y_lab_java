package ru.ylab.forms;

import lombok.Data;

/**
 * Class containing filters for searching users.
 *
 * @author azatyamanaev
 */
@Data
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
