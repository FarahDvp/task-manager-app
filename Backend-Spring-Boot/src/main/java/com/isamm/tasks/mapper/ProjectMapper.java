package com.isamm.tasks.mapper;

import com.isamm.tasks.dto.ProjectDTO;
import com.isamm.tasks.models.Project;
import org.mapstruct.Mapper;

// MapStruct-based mapper interface for converting between ProjectDTO and Project entities.
@Mapper(componentModel = "spring", uses = {TaskMapper.class})
public interface ProjectMapper extends EntityMapper<ProjectDTO, Project> {

    // Convert a Project entity to a ProjectDTO.
    ProjectDTO toDto(Project project);

    // Convert a ProjectDTO to a Project entity.
    Project toEntity(ProjectDTO projectDTO);

    // Convert an ID to a Project entity. Used for mapping an ID to a complete entity.
    default Project fromId(Long id) {
        if (id == null) {
            return null;
        }
        // Create a new Project entity with the provided ID.
        Project project = new Project();
        project.setId(id);
        return project;
    }
}
