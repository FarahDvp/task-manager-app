package com.isamm.tasks.services.Impl;
import com.isamm.tasks.dto.MemberDTO;
import com.isamm.tasks.dto.ProjectDTO;
import com.isamm.tasks.mapper.MemberMapper;
import com.isamm.tasks.models.Member;
import com.isamm.tasks.repository.MemberRepository;
import com.isamm.tasks.services.MemberService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Collections;
import java.util.List;
import java.util.Optional;


// Service class for managing Member entities.
@Service
@Transactional
public class MemberServiceImpl implements MemberService {

    // Autowired MemberRepository for interacting with the Member database.
    @Autowired
    private MemberRepository memberRepository;

    // Autowired MemberMapper for mapping between MemberDTO and Member entities.
    @Autowired
    private MemberMapper memberMapper;

    // Save a new member or update an existing one.
    @Override
    public MemberDTO save(MemberDTO memberDTO) {
        // Convert MemberDTO to Member entity and save it to the repository.
        Member member = memberMapper.toEntity(memberDTO);
        member = memberRepository.save(member);
        // Convert the saved Member entity back to MemberDTO and return it.
        return memberMapper.toDto(member);
    }

    // Retrieve all members.
    @Override
    public List<MemberDTO> findAll() {
        // Convert the list of Member entities to a list of MemberDTOs and return it.
        return memberMapper.toDto(memberRepository.findAll());
    }

    // Retrieve a member by its ID.
    @Override
    public MemberDTO findOne(Long id) {
        // Find the Member entity by ID in the repository.
        Optional<Member> member = memberRepository.findById(id);
        // Convert the found Member entity to MemberDTO and return it.
        return memberMapper.toDto(member.orElse(null));
    }

    // Delete a member by its ID.
    @Override
    public void delete(Long id) {
        // Delete the Member entity from the repository by ID.
        memberRepository.deleteById(id);
    }

    // Retrieve all projects and tasks associated with a member by username.
    @Override
    public List<ProjectDTO> getAllProjectsAndTasksByUsername(String username) {
        Optional<Member> memberOptional = memberRepository.findByUsername(username);

        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            // Retrieve and return the projects associated with the member.
            return memberMapper.toDto(member).getProjects();
        }

        return Collections.emptyList();
    }
}
