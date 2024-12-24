package com.isamm.tasks.mapper;

import com.isamm.tasks.dto.LabelDTO;
import com.isamm.tasks.models.Label;
import org.mapstruct.Mapper;

// MapStruct-based mapper interface for converting between LabelDTO and Label entities.
@Mapper(componentModel = "spring")
public interface LabelMapper extends EntityMapper<LabelDTO, Label> {

    // Convert a Label entity to a LabelDTO.
    LabelDTO toDto(Label label);

    // Convert a LabelDTO to a Label entity.
    Label toEntity(LabelDTO labelDTO);

    // Convert an ID to a Label entity. Used for mapping an ID to a complete entity.
    default Label fromId(Long id) {
        if (id == null) {
            return null;
        }
        // Create a new Label entity with the provided ID.
        Label label = new Label();
        label.setId(id);
        return label;
    }
}
