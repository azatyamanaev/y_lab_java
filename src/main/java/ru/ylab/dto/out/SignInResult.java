package ru.ylab.dto.out;

import java.util.Date;

/**
 * DTO for sending user after authorization.
 *
 * @param access access token
 * @param refresh refresh token
 * @param expires when refresh token expires
 * @author azatyamanaev
 */
public record SignInResult(String access, String refresh, Date expires) { }
