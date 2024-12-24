package com.isamm.tasks.services;


import com.isamm.tasks.dto.MemberDTO;
import com.isamm.tasks.dto.ProjectDTO;

import java.util.List;

// Interface defining the contract for managing members.
public interface MemberService {

    // Save a new member or update an existing one.
    MemberDTO save(MemberDTO memberDTO);

    // Retrieve all members.
    List<MemberDTO> findAll();

    // Retrieve a member by its ID.
    MemberDTO findOne(Long id);

    // Delete a member by its ID.
    void delete(Long id);

    // Retrieve all projects and tasks associated with a member by username.
    List<ProjectDTO> getAllProjectsAndTasksByUsername(String username);
}
