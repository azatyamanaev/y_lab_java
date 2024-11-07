package ru.ylab.core.services.auth;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.ylab.core.models.User;

/**
 * Class representing JWToken, generated when authorizing user.
 *
 * @author azatyamanaev
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JWToken {

    /**
     * Token id.
     */
    private String id;

    /**
     * User name.
     */
    private String username;

    /**
     * User role.
     */
    private User.Role role;

    /**
     * When this token was created.
     */
    private Instant created;

    /**
     * When this token expires
     */
    private Instant expires;
}
