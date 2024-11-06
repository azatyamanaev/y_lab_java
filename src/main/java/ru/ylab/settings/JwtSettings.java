package ru.ylab.settings;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Class containing settings for working with JWT tokens.
 *
 * @param accessTokenExpiration how long access token is valid in days
 * @param refreshTokenExpiration how long refresh token is valid in days
 * @param roleKey role claim name in JWT token
 * @param secretString secret string to create secret key with
 */
@ConfigurationProperties(prefix = "jwt")
public record JwtSettings(Integer accessTokenExpiration, Integer refreshTokenExpiration,
                          String roleKey, String secretString) {}
