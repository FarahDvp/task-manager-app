package com.isamm.tasks.controllers;

import com.isamm.tasks.dto.DatatablesRequest;
import com.isamm.tasks.dto.TaskDT;
import com.isamm.tasks.dto.TaskDTO;
import com.isamm.tasks.models.TaskStatistics;
import com.isamm.tasks.services.TaskService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@SecurityRequirement(name = "bearer-token")
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    // Constructor injection for TaskService
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // Create a new task
    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO) {
        // Save the task using TaskService and return the created task
        TaskDTO task = taskService.save(taskDTO);
        return ResponseEntity.ok().body(task);
    }

    // Get all tasks
    @GetMapping
    public ResponseEntity<List<TaskDTO>> findAllTasks() {
        // Retrieve all tasks using TaskService and return the list
        List<TaskDTO> tasks = taskService.findAll();
        return ResponseEntity.ok().body(tasks);
    }

    // Get a specific task by ID
    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> findOneTask(@PathVariable Long id) {
        // Retrieve a task by ID using TaskService and return it
        TaskDTO task = taskService.findOne(id);
        return ResponseEntity.ok().body(task);
    }

    // Delete a task by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        // Delete a task by ID using TaskService
        taskService.delete(id);
        return ResponseEntity.ok().build();
    }

    // Update a task (create if ID is null, update if ID is present)
    @PutMapping
    public ResponseEntity<TaskDTO> updateTask(@RequestBody TaskDTO taskDTO) {
        // If the task ID is null, create a new task; otherwise, update the existing task
        if (taskDTO.getId() == null) {
            return createTask(taskDTO);
        } else {
            TaskDTO task = taskService.save(taskDTO);
            return ResponseEntity.ok().body(task);
        }
    }

    // Mark a task as completed
    @PutMapping("/{taskId}/markAsCompleted")
    public ResponseEntity<TaskDTO> markTaskAsCompleted(@PathVariable Long taskId) {
        // Find the task by ID, mark it as completed, and return the updated task
        TaskDTO task = taskService.findOne(taskId);
        if (task != null) {
            task.setCompleted(true);
            taskService.save(task);
            return ResponseEntity.ok().body(task);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Get tasks by project ID
    @GetMapping("/{projectId}/tasks")
    public ResponseEntity<List<TaskDTO>> getTasksByProject(@PathVariable Long projectId) {
        // Retrieve tasks by project ID using TaskService and return the list
        List<TaskDTO> tasks = taskService.getTasksByProject(projectId);
        if (tasks.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().body(tasks);
        }
    }

    // Get tasks by label ID
    @GetMapping("/label/{labelId}/tasks")
    public ResponseEntity<List<TaskDTO>> getTasksByLabel(@PathVariable Long labelId) {
        // Retrieve tasks by label ID using TaskService and return the list
        List<TaskDTO> tasks = taskService.getTasksByLabel(labelId);
        if (tasks.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().body(tasks);
        }
    }

    // Get tasks with due date before a specified date
    @GetMapping("/due-date-before/{date}")
    public ResponseEntity<List<TaskDTO>> getTasksByDueDateBefore(@PathVariable String date) {
        // Retrieve tasks with due date before the specified date using TaskService and return the list
        List<TaskDTO> tasks = taskService.findByDueDateBefore(LocalDate.parse(date));
        if (tasks.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().body(tasks);
        }
    }

    // Get tasks with due date after a specified date
    @GetMapping("/due-date-after/{date}")
    public ResponseEntity<List<TaskDTO>> getTasksByDueDateAfter(@PathVariable String date) {
        // Retrieve tasks with due date after the specified date using TaskService and return the list
        List<TaskDTO> tasks = taskService.findByDueDateAfter(LocalDate.parse(date));
        if (tasks.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().body(tasks);
        }
    }

    // Move a task to trash
    @PutMapping("/{id}/to-trash")
    public ResponseEntity<TaskDTO> toTrash(@PathVariable Long id) {
        // Move a task to trash using TaskService and return the updated task
        TaskDTO task = taskService.toTrash(id);
        return ResponseEntity.ok().body(task);
    }

    // Move a task to the task list
    @PutMapping("/{id}/to-list-task")
    public ResponseEntity<TaskDTO> toListTask(@PathVariable Long id) {
        // Move a task to the task list using TaskService and return the updated task
        TaskDTO task = taskService.toListTask(id);
        return ResponseEntity.ok().body(task);
    }

    // Check if a task is delayed
    private boolean isTaskDelayed(TaskDTO task) {
        return ("IN_PROGRESS".equals(task.getStatus()) || "PENDING".equals(task.getStatus())) && LocalDate.now().isAfter(task.getDueDate());
    }

    // Get task statistics
    @GetMapping("/statistics")
    public TaskStatistics getTaskStatistics() {
        // Retrieve all tasks, calculate statistics, and return a TaskStatistics object
        List<TaskDTO> tasks = taskService.findAll();
        long allTasksCount = tasks.size();
        long doneTasksCount = tasks.stream().filter(task -> "DONE".equals(task.getStatus())).count();
        long delayedTasksCount = tasks.stream().filter(task -> isTaskDelayed(task)).count();

        return new TaskStatistics(doneTasksCount, delayedTasksCount, allTasksCount);
    }

    // Search for tasks based on DataTablesRequest parameters
    @PostMapping("/search")
    public ResponseEntity<TaskDT> getTasksWithSearch(@RequestBody DatatablesRequest datatablesRequest) {
        // Search for tasks based on DataTablesRequest parameters and return the results
        TaskDT dt = new TaskDT();

        try {
            final int page = (int) Math.ceil((double) datatablesRequest.getStart() / datatablesRequest.getLength());
            final PageRequest pr = PageRequest.of(page, datatablesRequest.getLength());
            final Page<TaskDTO> tasks = taskService.getTasksWithSearch(datatablesRequest.getSearchValue(), pr);
            dt.setRecordsFiltered(tasks.getTotalElements());
            dt.setRecordsTotal(tasks.getTotalElements());
            List<TaskDTO> taskDTOS = tasks.getContent();
            if (!CollectionUtils.isEmpty(taskDTOS)) {
                dt.setData(taskDTOS);
                return ResponseEntity.ok().body(dt);
            } else {
                return ResponseEntity.ok().body(dt);
            }
        } catch (Exception e) {
            // Log the exception or handle it appropriately
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Retrieve archived tasks based on DataTablesRequest parameters
    @PostMapping("/archived")
    public ResponseEntity<TaskDT> findAllArchived(@RequestBody DatatablesRequest datatablesRequest) {
        // Retrieve archived tasks based on DataTablesRequest parameters and return the results
        TaskDT dt = new TaskDT();
        try {
            final int page = (int) Math.ceil((double) datatablesRequest.getStart() / datatablesRequest.getLength());
            final PageRequest pr = PageRequest.of(page, datatablesRequest.getLength());
            final Page<TaskDTO> tasks = taskService.findAllArchived(pr);
            dt.setRecordsFiltered(tasks.getTotalElements());
            dt.setRecordsTotal(tasks.getTotalElements());
            List<TaskDTO> taskDTOS = tasks.getContent();
            if (!CollectionUtils.isEmpty(taskDTOS)) {
                dt.setData(taskDTOS);
                return ResponseEntity.ok().body(dt);
            } else {
                return ResponseEntity.ok().body(dt);
            }
        } catch (Exception e) {
            // Log the exception or handle it appropriately
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Retrieve tasks by member ID based on DataTablesRequest parameters
    @PostMapping("/member/{memberId}")
    public ResponseEntity<TaskDT> findTasksByMemberId(@RequestBody DatatablesRequest datatablesRequest, @PathVariable Long memberId) {
        // Retrieve tasks by member ID based on DataTablesRequest parameters and return the results
        TaskDT dt = new TaskDT();
        try {
            final int page = (int) Math.ceil((double) datatablesRequest.getStart() / datatablesRequest.getLength());
            final PageRequest pr = PageRequest.of(page, datatablesRequest.getLength());
            final Page<TaskDTO> tasks = taskService.findTasksByMemberId(pr, memberId);
            dt.setRecordsFiltered(tasks.getTotalElements());
            dt.setRecordsTotal(tasks.getTotalElements());
            List<TaskDTO> taskDTOS = tasks.getContent();
            if (!CollectionUtils.isEmpty(taskDTOS)) {
                dt.setData(taskDTOS);
                return ResponseEntity.ok().body(dt);
            } else {
                return ResponseEntity.ok().body(dt);
            }
        } catch (Exception e) {
            // Log the exception or handle it appropriately
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Retrieve tasks by member ID and project ID
    @GetMapping("/project/{projectId}/member/{memberId}")
    public ResponseEntity<List<TaskDTO>> findTasksByMemberIdAndProjectId(@PathVariable Long memberId, @PathVariable Long projectId) {
        // Retrieve tasks by member ID and project ID using TaskService and return the list
        List<TaskDTO> tasks = taskService.findTasksByMemberIdAndProjectId(memberId, projectId);
        return ResponseEntity.ok().body(tasks);
    }
}
