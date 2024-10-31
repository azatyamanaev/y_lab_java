package ru.ylab.dto.in;

import lombok.Getter;
import lombok.Setter;
import ru.ylab.models.User;

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
    private User.Role role;
}
