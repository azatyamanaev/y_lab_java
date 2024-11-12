package ru.ylab.core.models;

import java.time.Instant;

import lombok.Getter;
import lombok.Setter;

/**
 * Class for storing user request data.
 *
 * @author azatyamanaev
 */
@Getter
@Setter
public class UserRequest {

    /**
     * Request id.
     */
    private Long id;

    /**
     * Http method.
     */
    private String method;

    /**
     * Http request uri.
     */
    private String uri;

    /**
     * User id.
     */
    private Long userId;

    /**
     * User role.
     */
    private String role;

    /**
     * When Http request was executed.
     */
    private Instant timestamp;
}
