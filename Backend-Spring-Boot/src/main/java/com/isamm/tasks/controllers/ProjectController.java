package com.isamm.tasks.controllers;

import com.isamm.tasks.dto.ProjectDTO;
import com.isamm.tasks.dto.TaskDTO;
import com.isamm.tasks.services.ProjectService;
import com.isamm.tasks.services.TaskService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@SecurityRequirement(name = "bearer-token")
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TaskService taskService;

    // Create a new project
    @PostMapping
    public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectDTO projectDTO) {
        // Save the project using ProjectService and return the created project
        ProjectDTO project = projectService.save(projectDTO);
        return ResponseEntity.ok().body(project);
    }

    // Get all projects
    @GetMapping
    public ResponseEntity<List<ProjectDTO>> findAllProjects() {
        // Retrieve all projects using ProjectService and return the list
        List<ProjectDTO> projects = projectService.findAll();
        return ResponseEntity.ok().body(projects);
    }

    // Get a specific project by ID
    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> findOneProject(@PathVariable Long id) {
        // Retrieve a project by ID using ProjectService and return it
        ProjectDTO project = projectService.findOne(id);
        return ResponseEntity.ok().body(project);
    }

    // Delete a project by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        // Delete a project by ID using ProjectService
        projectService.delete(id);
        return ResponseEntity.ok().build();
    }

    // Update a project (create if ID is null, update if ID is present)
    @PutMapping
    public ResponseEntity<ProjectDTO> updateProject(@RequestBody ProjectDTO projectDTO) {
        // If the project ID is null, create a new project; otherwise, update the existing project
        if (projectDTO.getId() == null) {
            return createProject(projectDTO);
        } else {
            ProjectDTO project = projectService.save(projectDTO);
            return ResponseEntity.ok().body(project);
        }
    }

    // Get projects by member ID
    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<ProjectDTO>> findProjectsByMemberId(@PathVariable Long memberId) {
        // Retrieve projects by member ID using ProjectService and return the list
        List<ProjectDTO> projects = projectService.findProjectsByMemberId(memberId);
        return ResponseEntity.ok().body(projects);
    }
}

