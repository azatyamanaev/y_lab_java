package ru.ylab.models;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class for storing refresh tokens.
 *
 * @author azatyamanaev
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {

    /**
     * Token id.
     */
    private Long id;

    /**
     * JWToken
     */
    private String token;

    /**
     * User id.
     */
    private Long userId;

    /**
     * When token was created.
     */
    private Instant created;

    /**
     * When token expires.
     */
    private Instant expires;
}
