package com.isamm.tasks.mapper;

import java.util.List;

// Interface defining methods for mapping between Data Transfer Objects (DTOs) and entities.
public interface EntityMapper<D, E> {

    // Convert a DTO to an entity.
    public E toEntity(D dto);

    // Convert an entity to a DTO.
    public D toDto(E entity);

    // Convert a list of DTOs to a list of entities.
    public List<E> toEntity(List<D> dtoList);

    // Convert a list of entities to a list of DTOs.
    public List<D> toDto(List<E> entityList);
}
