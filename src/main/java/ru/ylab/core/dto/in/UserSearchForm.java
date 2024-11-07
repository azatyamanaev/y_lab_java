package ru.ylab.core.dto.in;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springdoc.core.annotations.ParameterObject;
import ru.ylab.core.models.User;

/**
 * Class containing filters for searching users.
 *
 * @author azatyamanaev
 */
@Getter
@Setter
@ParameterObject
@EqualsAndHashCode
public class UserSearchForm {

    /**
     * User name.
     */
    @Parameter
    private String name;

    /**
     * User email.
     */
    @Parameter
    private String email;

    /**
     * User role.
     */
    @Parameter
    private User.Role role;
}
