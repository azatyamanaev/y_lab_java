package ru.ylab.core.dto.mappers;

import org.mapstruct.Mapper;
import ru.ylab.core.dto.mappers.base.DtoMapper;
import ru.ylab.core.dto.out.UserRequestDto;
import ru.ylab.core.models.UserRequest;

@Mapper(componentModel = "spring")
public interface UserRequestMapper extends DtoMapper<UserRequest, UserRequestDto> {

    @Override
    UserRequestDto mapToDto(UserRequest userRequest);
}
