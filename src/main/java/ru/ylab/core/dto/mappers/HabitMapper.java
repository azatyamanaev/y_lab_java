package ru.ylab.core.dto.mappers;

import org.mapstruct.Mapper;
import ru.ylab.core.dto.mappers.base.DtoMapper;
import ru.ylab.core.dto.out.HabitDto;
import ru.ylab.core.models.Habit;

/**
 * Mapper for {@link Habit} to {@link HabitDto} mapping.
 *
 * @author azatyamanaev
 */
@Mapper(componentModel = "spring")
public interface HabitMapper extends DtoMapper<Habit, HabitDto> {

    @Override
    HabitDto mapToDto(Habit habit);
}
