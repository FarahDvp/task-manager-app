package com.isamm.tasks.services;

import com.isamm.tasks.dto.MemberDTO;
import com.isamm.tasks.dto.TaskDTO;
import com.isamm.tasks.models.Label;
import com.isamm.tasks.models.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

// Interface defining the contract for managing tasks.
public interface TaskService {

    // Save a new task or update an existing one.
    TaskDTO save(TaskDTO taskDTO);

    // Retrieve all tasks.
    List<TaskDTO> findAll();

    // Retrieve a task by its ID.
    TaskDTO findOne(Long id);

    // Delete a task by its ID.
    void delete(Long id);

    // Retrieve tasks associated with a project by project ID.
    List<TaskDTO> getTasksByProject(Long projectId);

    // Retrieve tasks associated with a label by label ID.
    List<TaskDTO> getTasksByLabel(Long labelId);

    // Retrieve tasks with due dates before a specified date.
    List<TaskDTO> findByDueDateBefore(LocalDate date);

    // Retrieve tasks with due dates after a specified date.
    List<TaskDTO> findByDueDateAfter(LocalDate date);

    // Move a task to the "ARCHIVED" status.
    TaskDTO toTrash(Long id);

    // Move a task to the "TODO" status.
    TaskDTO toListTask(Long id);

    // Retrieve paginated tasks with a search term.
    Page<TaskDTO> getTasksWithSearch(String searchTerm, Pageable pageable);

    // Retrieve paginated archived tasks.
    Page<TaskDTO> findAllArchived(Pageable pageable);

    // Retrieve tasks associated with a member and a project.
    List<TaskDTO> findTasksByMemberIdAndProjectId(Long memberId, Long projectId);

    // Retrieve paginated tasks associated with a member.
    Page<TaskDTO> findTasksByMemberId(Pageable pageable, Long memberId);
}
