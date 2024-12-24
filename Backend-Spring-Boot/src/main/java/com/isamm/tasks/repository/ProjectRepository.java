package com.isamm.tasks.repository;
import com.isamm.tasks.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


// Repository interface for managing projects in the database.
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    // Retrieve projects by member ID using a JPQL query.
    @Query("SELECT p FROM Project p JOIN p.members m WHERE m.id = :memberId")
    List<Project> findProjectsByMemberId(@Param("memberId") Long memberId);
}
