package ru.ylab.dto.mappers;

import org.mapstruct.Mapper;
import ru.ylab.dto.mappers.base.DtoMapper;
import ru.ylab.dto.out.UserDto;
import ru.ylab.models.User;

/**
 * Mapper for {@link User} to {@link UserDto} mapping.
 *
 * @author azatyamanaev
 */
@Mapper
public interface UserMapper extends DtoMapper<User, UserDto> {

    @Override
    UserDto mapToDto(User user);
}
