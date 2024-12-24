package com.isamm.tasks.controllers;

import com.isamm.tasks.dto.MemberDTO;
import com.isamm.tasks.dto.ProjectDTO;
import com.isamm.tasks.services.MemberService;
import com.isamm.tasks.services.ProjectService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@SecurityRequirement(name = "bearer-token")
@RequestMapping("/api/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private ProjectService projectService;

    // Create a new member
    @PostMapping
    public ResponseEntity<MemberDTO> create(@RequestBody MemberDTO memberDTO) {
        // Save the member using MemberService and return the created member
        MemberDTO member = memberService.save(memberDTO);
        return ResponseEntity.ok().body(member);
    }

    // Get all members
    @GetMapping
    public ResponseEntity<List<MemberDTO>> findAll() {
        // Retrieve all members using MemberService and return the list
        List<MemberDTO> members = memberService.findAll();
        return ResponseEntity.ok().body(members);
    }

    // Get a specific member by ID
    @GetMapping("/{id}")
    public ResponseEntity<MemberDTO> findOne(@PathVariable Long id) {
        // Retrieve a member by ID using MemberService and return it
        MemberDTO member = memberService.findOne(id);
        return ResponseEntity.ok().body(member);
    }

    // Get all projects and tasks for a member by username
    @GetMapping("/tasks/{username}")
    public ResponseEntity<List<ProjectDTO>> getAllProjectsAndTasksByUsername(@PathVariable String username) {
        // Retrieve all projects and tasks for a member by username using MemberService and return the list
        List<ProjectDTO> listProject = memberService.getAllProjectsAndTasksByUsername(username);
        return ResponseEntity.ok().body(listProject);
    }

    // Delete a member by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // Delete a member by ID using MemberService
        memberService.delete(id);
        return ResponseEntity.ok().build();
    }

    // Update a member (create if ID is null, update if ID is present)
    @PutMapping
    public ResponseEntity<MemberDTO> update(@RequestBody MemberDTO memberDTO) {
        // If the member ID is null, create a new member; otherwise, update the existing member
        if (memberDTO.getId() == null) {
            return create(memberDTO);
        } else {
            MemberDTO member = memberService.save(memberDTO);
            return ResponseEntity.ok().body(member);
        }
    }

    // Assign a project to a member
    @PutMapping("/{memberId}/assignProject/{projectId}")
    public ResponseEntity<MemberDTO> assignProjectToMember(@PathVariable Long memberId, @PathVariable Long projectId) {
        // Find the member and project by IDs, assign the project to the member, and return the updated member
        MemberDTO member = memberService.findOne(memberId);
        ProjectDTO project = projectService.findOne(projectId);

        if (member != null && project != null) {
            List<ProjectDTO> memberProjects = member.getProjects();
            memberProjects.add(project);
            member.setProjects(memberProjects);
            memberService.save(member);
            return ResponseEntity.ok().body(member);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
