package ru.ylab.core.dto.mappers;

import org.mapstruct.Mapper;
import ru.ylab.core.dto.mappers.base.DtoMapper;
import ru.ylab.core.dto.out.UserDto;
import ru.ylab.core.models.User;

/**
 * Mapper for {@link User} to {@link UserDto} mapping.
 *
 * @author azatyamanaev
 */
@Mapper(componentModel = "spring")
public interface UserMapper extends DtoMapper<User, UserDto> {

    @Override
    UserDto mapToDto(User user);
}
