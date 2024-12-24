package com.isamm.tasks.services.Impl;

import com.isamm.tasks.dto.ProjectDTO;
import com.isamm.tasks.mapper.ProjectMapper;
import com.isamm.tasks.models.Project;
import com.isamm.tasks.repository.ProjectRepository;
import com.isamm.tasks.services.ProjectService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
// Service class for managing Project entities.
@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    // Autowired ProjectRepository for interacting with the Project database.
    @Autowired
    private ProjectRepository projectRepository;

    // Autowired ProjectMapper for mapping between ProjectDTO and Project entities.
    @Autowired
    private ProjectMapper projectMapper;

    // Save a new project or update an existing one.
    @Override
    public ProjectDTO save(ProjectDTO projectDTO) {
        // Convert ProjectDTO to Project entity and save it to the repository.
        Project project = projectMapper.toEntity(projectDTO);
        project = projectRepository.save(project);
        // Convert the saved Project entity back to ProjectDTO and return it.
        return projectMapper.toDto(project);
    }

    // Retrieve all projects.
    @Override
    public List<ProjectDTO> findAll() {
        // Convert the list of Project entities to a list of ProjectDTOs and return it.
        return projectMapper.toDto(projectRepository.findAll());
    }

    // Retrieve a project by its ID.
    @Override
    public ProjectDTO findOne(Long id) {
        // Find the Project entity by ID in the repository.
        Optional<Project> project = projectRepository.findById(id);
        // Convert the found Project entity to ProjectDTO and return it.
        return projectMapper.toDto(project.orElse(null));
    }

    // Delete a project by its ID.
    @Override
    public void delete(Long id) {
        // Delete the Project entity from the repository by ID.
        projectRepository.deleteById(id);
    }

    // Retrieve all projects associated with a member by member ID.
    @Override
    public List<ProjectDTO> findProjectsByMemberId(Long memberId) {
        // Convert the list of Project entities associated with a member to a list of ProjectDTOs and return it.
        return projectMapper.toDto(projectRepository.findProjectsByMemberId(memberId));
    }
}
