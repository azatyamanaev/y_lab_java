package ru.ylab.core.dto.out;

/**
 * User request DTO for sending in server responses.
 *
 * @param method Http request method
 * @param uri Http request uri
 * @param userId user id
 * @param role user role
 */
public record UserRequestDto(String method, String uri, Long userId, String role) {}
