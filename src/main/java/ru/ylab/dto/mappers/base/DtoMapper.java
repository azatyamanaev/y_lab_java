package ru.ylab.dto.mappers.base;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

/**
 * Basic entity to dto mapper.
 *
 * @param <E> entity class
 * @param <D> dto class
 * @author azatyamanaev
 */
public interface DtoMapper<E, D> extends Function<E, D> {

    /**
     * Maps list of entities to list of dtos.
     *
     * @param items list of entities
     * @return list of dtos
     */
    default List<D> apply(List<? extends E> items) {
        if (CollectionUtils.isEmpty(items)) {
            return Collections.emptyList();
        }
        return items.stream()
                .map(this)
                .collect(Collectors.toList());
    }
}
