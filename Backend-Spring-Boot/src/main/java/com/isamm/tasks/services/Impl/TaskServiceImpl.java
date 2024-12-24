package com.isamm.tasks.services.Impl;

import com.isamm.tasks.dto.TaskDTO;
import com.isamm.tasks.mapper.MemberMapper;
import com.isamm.tasks.mapper.TaskMapper;
import com.isamm.tasks.models.Task;
import com.isamm.tasks.repository.TaskRepository;
import com.isamm.tasks.services.TaskService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// Service class for managing Task entities.
@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    // Autowired TaskRepository for interacting with the Task database.
    @Autowired
    private TaskRepository taskRepository;

    // Autowired TaskMapper for mapping between TaskDTO and Task entities.
    @Autowired
    private TaskMapper taskMapper;

    // Autowired MemberMapper for mapping between MemberDTO and Member entities.
    @Autowired
    private MemberMapper memberMapper;

    // Save a new task or update an existing one.
    @Override
    public TaskDTO save(TaskDTO taskDTO) {
        // Convert TaskDTO to Task entity and save it to the repository.
        Task task = taskMapper.toEntity(taskDTO);
        task = taskRepository.save(task);
        // Convert the saved Task entity back to TaskDTO and return it.
        return taskMapper.toDto(task);
    }

    // Retrieve all tasks.
    @Override
    public List<TaskDTO> findAll() {
        // Convert the list of Task entities to a list of TaskDTOs and return it.
        return taskMapper.toDto(taskRepository.findAll());
    }

    // Retrieve a task by its ID.
    @Override
    public TaskDTO findOne(Long id) {
        // Find the Task entity by ID in the repository.
        Optional<Task> task = taskRepository.findById(id);
        // Convert the found Task entity to TaskDTO and return it.
        return taskMapper.toDto(task.orElse(null));
    }

    // Delete a task by its ID.
    @Override
    public void delete(Long id) {
        // Delete the Task entity from the repository by ID.
        taskRepository.deleteById(id);
    }

    // Retrieve all tasks associated with a project by project ID.
    @Override
    public List<TaskDTO> getTasksByProject(Long projectId) {
        // Retrieve tasks associated with the project and convert them to TaskDTOs.
        List<Task> tasks = taskRepository.findByProjectId(projectId);
        return tasks.stream().map(taskMapper::toDto).collect(Collectors.toList());
    }

    // Retrieve all tasks associated with a label by label ID.
    @Override
    public List<TaskDTO> getTasksByLabel(Long labelId) {
        // Retrieve tasks associated with the label and convert them to TaskDTOs.
        List<Task> tasks = taskRepository.findByLabelId(labelId);
        return tasks.stream().map(taskMapper::toDto).collect(Collectors.toList());
    }

    // Retrieve tasks with due dates before a specified date.
    @Override
    public List<TaskDTO> findByDueDateBefore(LocalDate date) {
        // Retrieve tasks with due dates before the specified date and convert them to TaskDTOs.
        List<Task> tasks = taskRepository.findByDueDateBefore(date);
        return tasks.stream().map(taskMapper::toDto).collect(Collectors.toList());
    }

    // Retrieve tasks with due dates after a specified date.
    @Override
    public List<TaskDTO> findByDueDateAfter(LocalDate date) {
        // Retrieve tasks with due dates after the specified date and convert them to TaskDTOs.
        List<Task> tasks = taskRepository.findByDueDateAfter(date);
        return tasks.stream().map(taskMapper::toDto).collect(Collectors.toList());
    }

    // Move a task to the "ARCHIVED" status.
    @Override
    public TaskDTO toTrash(Long id) {
        // Retrieve the task by ID, set its status to "ARCHIVED", save it, and convert it to TaskDTO.
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        task.setStatus("ARCHIVED");
        task = taskRepository.save(task);
        return taskMapper.toDto(task);
    }

    // Move a task to the "TODO" status.
    @Override
    public TaskDTO toListTask(Long id) {
        // Retrieve the task by ID, set its status to "TODO", save it, and convert it to TaskDTO.
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        task.setStatus("TODO");
        task = taskRepository.save(task);
        return taskMapper.toDto(task);
    }

    // Retrieve tasks with a search term and paginate the results.
    @Override
    public Page<TaskDTO> getTasksWithSearch(String searchTerm, Pageable pageable) {
        Page<Task> tasksPage;
        if (StringUtils.hasText(searchTerm)) {
            // If a search term is provided, perform a search using the term.
            tasksPage = taskRepository.findBySearchTerm(searchTerm.toLowerCase(), pageable);
        } else {
            // If no search term is provided, retrieve all tasks.
            tasksPage = taskRepository.findAll(pageable);
        }
        // Convert the Page of Task entities to a Page of TaskDTOs and return it.
        return tasksPage.map(taskMapper::toDto);
    }

    // Retrieve all archived tasks and paginate the results.
    @Override
    public Page<TaskDTO> findAllArchived(Pageable pageable) {
        // Retrieve all archived tasks and convert them to TaskDTOs.
        Page<Task> tasksPage = taskRepository.findAllArchived(pageable);
        return tasksPage.map(taskMapper::toDto);
    }

    // Retrieve tasks associated with a member and a project.
    @Override
    public List<TaskDTO> findTasksByMemberIdAndProjectId(Long memberId, Long projectId) {
        // Retrieve tasks associated with a member and a project, and convert them to TaskDTOs.
        List<Task> tasks = taskRepository.findTasksByMemberIdAndProjectId(memberId, projectId);
        return tasks.stream().map(taskMapper::toDto).collect(Collectors.toList());
    }

    // Retrieve paginated tasks associated with a member.
    @Override
    public Page<TaskDTO> findTasksByMemberId(Pageable pageable, Long memberId) {
        // Retrieve paginated tasks associated with a member, and convert them to TaskDTOs.
        Page<Task> tasksPage = taskRepository.findTasksByMemberId(pageable, memberId);
        return tasksPage.map(taskMapper::toDto);
    }
}
