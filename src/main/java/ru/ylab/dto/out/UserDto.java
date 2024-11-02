package ru.ylab.dto.out;

import io.swagger.v3.oas.annotations.media.Schema;
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
public record UserDto(

        @Schema(description = "user id")
        Long id,

        @Schema(description = "user email")
        String email,

        @Schema(description = "user name")
        String name,

        @Schema(description = "user role")
        User.Role role
) {}
