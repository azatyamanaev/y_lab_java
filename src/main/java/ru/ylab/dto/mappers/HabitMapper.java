package ru.ylab.dto.mappers;

import org.mapstruct.Mapper;
import ru.ylab.dto.mappers.base.DtoMapper;
import ru.ylab.dto.out.HabitDto;
import ru.ylab.models.Habit;

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
