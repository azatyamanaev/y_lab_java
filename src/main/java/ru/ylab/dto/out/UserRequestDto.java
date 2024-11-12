package ru.ylab.dto.out;

import java.time.Instant;

/**
 * User request DTO for sending in server responses.
 *
 * @param method Http request method
 * @param uri Http request uri
 * @param userId user id
 * @param role user role
 * @param timestamp when user request was executed
 */
public record UserRequestDto(String method, String uri, Long userId, String role, Instant timestamp) {}
