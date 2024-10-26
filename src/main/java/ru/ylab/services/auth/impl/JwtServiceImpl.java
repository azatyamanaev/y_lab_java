package ru.ylab.services.auth.impl;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import ru.ylab.dto.out.SignInResult;
import ru.ylab.models.RefreshToken;
import ru.ylab.models.User;
import ru.ylab.repositories.RefreshTokenRepository;
import ru.ylab.services.auth.JWToken;
import ru.ylab.services.auth.JwtService;
import ru.ylab.services.entities.UserService;

/**
 * Class implementing {@link JwtService}.
 *
 * @author azatyamanaev
 */
@Slf4j
public class JwtServiceImpl implements JwtService {

    /**
     * How long in days access token is valid.
     */
    private static final int ACCESS_TOKEN_EXPIRATION = 1;

    /**
     * How long in days refresh token is valid.
     */
    private static final int REFRESH_TOKEN_EXPIRATION = 14;

    /**
     *
     */
    private static final String ROLE_KEY = "role";

    private static final String SECRET_STRING = "habits-app-yLRdtN3NsFRHKkThmB6EN2QXLxXGTPa7bgzx0pY72";
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET_STRING.getBytes(StandardCharsets.UTF_8));

    /**
     * Instance of a {@link RefreshTokenRepository}.
     */
    private final RefreshTokenRepository tokenRepository;

    /**
     * Instance of a {@link UserService}.
     */
    private final UserService userService;

    /**
     * Creates new JwtServiceImpl.
     *
     * @param tokenRepository TokenRepository instance
     */
    public JwtServiceImpl(RefreshTokenRepository tokenRepository, UserService userService) {
        this.tokenRepository = tokenRepository;
        this.userService = userService;
    }

    @Override
    public JWToken parse(String token) {
        final Claims claims = Jwts.parser()
                                  .verifyWith(SECRET_KEY)
                                  .build()
                                  .parseSignedClaims(token)
                                  .getPayload();

        User.Role role = User.Role.valueOf(claims.get(ROLE_KEY, String.class));
        return new JWToken(claims.getId(), claims.getSubject(), role,
                claims.getIssuedAt().toInstant(), claims.getExpiration().toInstant());
    }

    @Override
    public SignInResult createToken(@NotNull User user) {
        String access = generateAccess(user.getEmail(), user.getRole());
        String refresh = generateRefresh();
        Instant now = Instant.now();
        Instant expires = now.plus(REFRESH_TOKEN_EXPIRATION, ChronoUnit.DAYS);
        RefreshToken refreshToken = RefreshToken.builder()
                                                .token(refresh)
                                                .userId(user.getId())
                                                .created(now)
                                                .expires(expires)
                                                .build();
        tokenRepository.save(refreshToken);
        return new SignInResult(access, refresh, Date.from(expires));
    }

    @Override
    public String generateAccess(String refresh) {
        RefreshToken refreshToken = tokenRepository.getByToken(refresh);
        if (refreshToken == null) {
            log.error("Refresh token {} not found", refresh);
            return null;
        } else {
            User user = userService.get(refreshToken.getUserId());
            if (user == null) {
                log.error("User {} not found", refresh);
                return null;
            } else {
                return generateAccess(user.getEmail(), user.getRole());
            }
        }
    }

    public String generateAccess(String username, User.Role role) {
        Instant now = Instant.now();
        Instant expires = now.plus(ACCESS_TOKEN_EXPIRATION, ChronoUnit.DAYS);
        return generate(username, role, now, expires);
    }

    public String generateRefresh() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        return Base64.getEncoder().encodeToString(bytes);
    }

    private String generate(String username, User.Role role, Instant issued, Instant expiration) {
        return Jwts.builder()
                   .claim(ROLE_KEY, role.name())
                   .claims()
                   .id(UUID.randomUUID().toString())
                   .subject(username)
                   .issuedAt(Date.from(issued))
                   .expiration(Date.from(expiration))
                   .and().signWith(SECRET_KEY).compact();
    }
}
