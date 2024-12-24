package com.isamm.tasks.mapper;

import com.isamm.tasks.dto.TaskDTO;
import com.isamm.tasks.models.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

// MapStruct-based mapper interface for converting between TaskDTO and Task entities.
@Mapper(componentModel = "spring", uses = {LabelMapper.class, ProjectMapper.class, MemberMapper.class})
public interface TaskMapper extends EntityMapper<TaskDTO, Task> {

    // Convert a Task entity to a TaskDTO, mapping the project ID and name to separate fields.
    @Mapping(source = "project.id", target = "projectId")
    @Mapping(source = "project.name", target = "projectName")
    TaskDTO toDto(Task task);

    // Convert a TaskDTO to a Task entity, mapping the projectId to the project field.
    @Mapping(source = "projectId", target = "project")
    Task toEntity(TaskDTO taskDTO);

    // Convert an ID to a Task entity. Used for mapping an ID to a complete entity.
    default Task fromId(Long id) {
        if (id == null) {
            return null;
        }
        // Create a new Task entity with the provided ID.
        Task task = new Task();
        task.setId(id);
        return task;
    }
}
