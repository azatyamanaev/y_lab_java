package ru.ylab.dto.mappers;

import org.mapstruct.Mapper;
import ru.ylab.dto.mappers.base.DtoMapper;
import ru.ylab.dto.out.UserRequestDto;
import ru.ylab.models.UserRequest;

@Mapper(componentModel = "spring")
public interface UserRequestMapper extends DtoMapper<UserRequest, UserRequestDto> {

    @Override
    UserRequestDto mapToDto(UserRequest userRequest);
}
