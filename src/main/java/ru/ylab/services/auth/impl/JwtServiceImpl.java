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
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import ru.ylab.dto.out.SignInResult;
import ru.ylab.exception.HttpException;
import ru.ylab.models.RefreshToken;
import ru.ylab.models.User;
import ru.ylab.repositories.RefreshTokenRepository;
import ru.ylab.services.auth.JWToken;
import ru.ylab.services.auth.JwtService;
import ru.ylab.services.entities.UserService;
import ru.ylab.settings.JwtSettings;
import ru.ylab.utils.constants.ErrorConstants;

/**
 * Class implementing {@link JwtService}.
 *
 * @author azatyamanaev
 */
@Service
public class JwtServiceImpl implements JwtService {

    private SecretKey secretKey;

    private final RefreshTokenRepository tokenRepository;
    private final UserService userService;
    private final JwtSettings settings;

    public JwtServiceImpl(RefreshTokenRepository tokenRepository, UserService userService, JwtSettings settings) {
        this.tokenRepository = tokenRepository;
        this.userService = userService;
        this.settings = settings;
        this.secretKey = Keys.hmacShaKeyFor(settings.secretString().getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public JWToken parse(String token) {
        final Claims claims = Jwts.parser()
                                  .verifyWith(secretKey)
                                  .build()
                                  .parseSignedClaims(token)
                                  .getPayload();

        User.Role role = User.Role.valueOf(claims.get(settings.roleKey(), String.class));
        return new JWToken(claims.getId(), claims.getSubject(), role,
                claims.getIssuedAt().toInstant(), claims.getExpiration().toInstant());
    }

    @Override
    public SignInResult createToken(@NotNull User user) {
        String access = generateAccess(user.getEmail(), user.getRole());
        String refresh = generateRefresh();
        Instant now = Instant.now();
        Instant expires = now.plus(settings.refreshTokenExpiration(), ChronoUnit.DAYS);
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
        RefreshToken refreshToken =
                tokenRepository.findByToken(refresh)
                               .orElseThrow(() -> HttpException.badRequest()
                                                               .addDetail(ErrorConstants.NOT_FOUND, "refresh token"));
        User user = userService.get(refreshToken.getUserId());
        return generateAccess(user.getEmail(), user.getRole());
    }

    public String generateAccess(String username, User.Role role) {
        Instant now = Instant.now();
        Instant expires = now.plus(settings.accessTokenExpiration(), ChronoUnit.DAYS);
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
                   .claim(settings.roleKey(), role.name())
                   .claims()
                   .id(UUID.randomUUID().toString())
                   .subject(username)
                   .issuedAt(Date.from(issued))
                   .expiration(Date.from(expiration))
                   .and().signWith(secretKey).compact();
    }
}
