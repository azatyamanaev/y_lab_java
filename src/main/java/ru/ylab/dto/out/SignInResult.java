package ru.ylab.dto.out;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO for sending user after authorization.
 *
 * @param access access token
 * @param refresh refresh token
 * @param expires when refresh token expires
 * @author azatyamanaev
 */
public record SignInResult(

        @Schema(description = "access token")
        String access,

        @Schema(description = "refresh token")
        String refresh,

        @Schema(description = "when token expires")
        Date expires
) { }
