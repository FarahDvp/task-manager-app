package com.isamm.tasks.services;


import com.isamm.tasks.dto.ProjectDTO;
import com.isamm.tasks.models.Project;

import java.util.List;
// Interface defining the contract for managing projects.
public interface ProjectService {

    // Save a new project or update an existing one.
    ProjectDTO save(ProjectDTO projectDTO);

    // Retrieve all projects.
    List<ProjectDTO> findAll();

    // Retrieve a project by its ID.
    ProjectDTO findOne(Long id);

    // Delete a project by its ID.
    void delete(Long id);

    // Retrieve projects associated with a member by member ID.
    List<ProjectDTO> findProjectsByMemberId(Long memberId);
}
