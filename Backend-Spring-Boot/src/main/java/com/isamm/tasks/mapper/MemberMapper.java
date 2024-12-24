package com.isamm.tasks.mapper;

import com.isamm.tasks.dto.MemberDTO;
import com.isamm.tasks.models.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

// MapStruct-based mapper interface for converting between MemberDTO and Member entities.
@Mapper(componentModel = "spring", uses = {ProjectMapper.class})
public interface MemberMapper extends EntityMapper<MemberDTO, Member> {

    // Convert a Member entity to a MemberDTO, ignoring the "projects" field.
    @Mapping(target = "projects", ignore = true)
    MemberDTO toDto(Member member);

    // Convert a MemberDTO to a Member entity, ignoring the "projects" field.
    @Mapping(target = "projects", ignore = true)
    Member toEntity(MemberDTO memberDTO);

    // Convert an ID to a Member entity. Used for mapping an ID to a complete entity.
    default Member fromId(Long id) {
        if (id == null) {
            return null;
        }
        // Create a new Member entity with the provided ID.
        Member member = new Member();
        member.setId(id);
        return member;
    }
}
