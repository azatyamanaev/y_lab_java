package ru.ylab.dto.out;

import ru.ylab.models.User;

/**
 * User DTO for sending in server responses.
 *
 * @param id user id
 * @param email user email
 * @param name user name
 * @param role user role
 * @author azatyamanaev
 */
public record UserDto(Long id, String email, String name,
                      User.Role role) {
}
