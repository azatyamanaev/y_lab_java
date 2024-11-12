package ru.ylab.repositories;

import java.util.Optional;

import ru.ylab.models.RefreshToken;

/**
 * Interface describing logic for working with refresh tokens storage.
 *
 * @author azatyamanaev
 */
public interface RefreshTokenRepository {

    /**
     * Finds refresh token by token.
     *
     * @param token token
     * @return {@code Optional<RefreshToken>}
     */
    Optional<RefreshToken> findByToken(String token);

    /**
     * Saves refresh token.
     *
     * @param refreshToken refresh token
     * @return whether save was successful
     */
    boolean save(RefreshToken refreshToken);
}
