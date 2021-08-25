package com.ilya.service.modelmapper;


import com.ilya.db.domain.Base;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


/**
 * D - Dto
 * E - Entity
 */

public interface BaseMapper<D, E extends Base> {

    E convertToEntity(final D input);

    D convertToDto(final E input);

    default List<E> convertListToEntity(final Collection<D> input) {
        return input.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());
    }

    default List<D> convertListToDto(final Collection<E> input) {
        return input.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

}
