package ru.ylab.dto.mappers.base;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

/**
 * Basic entity to dto mapper.
 *
 * @param <E> entity class
 * @param <D> dto class
 * @author azatyamanaev
 */
public interface DtoMapper<E, D> {

    /**
     * Maps entity to dto
     *
     * @param e entity to map
     * @return dto
     */
    D mapToDto(E e);

    /**
     * Maps list of entities to list of dtos.
     *
     * @param items list of entities
     * @return list of dtos
     */
    default List<D> mapToDto(List<? extends E> items) {
        if (CollectionUtils.isEmpty(items)) {
            return Collections.emptyList();
        }
        return items.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}
