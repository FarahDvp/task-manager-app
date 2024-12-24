package com.isamm.tasks.repository;

import com.isamm.tasks.models.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


// Repository interface for managing members in the database.
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

	// Retrieve a member by username using a custom query.
	Optional<Member> findByUsername(String username);
}
