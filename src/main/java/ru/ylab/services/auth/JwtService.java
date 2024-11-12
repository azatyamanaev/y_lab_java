package ru.ylab.services.auth;

import jakarta.validation.constraints.NotNull;
import ru.ylab.dto.out.SignInResult;
import ru.ylab.models.User;

/**
 * Interface describing logic for working with JWTokens.
 *
 * @author azatyamanaev
 */
public interface JwtService {

    /**
     * Parses JWToken from string.
     *
     * @param token string to parse
     * @return JWToken instance
     */
    JWToken parse(String token);

    /**
     * Creates access and refresh tokens during user authorization.
     * Also saves refresh token to database.
     *
     * @param user user data
     * @return TokenDto instance
     */
    SignInResult createToken(@NotNull User user);

    /**
     * Generates access token by refresh token.
     *
     * @param refresh refresh token
     * @return access token
     */
    String generateAccess(String refresh);
}
