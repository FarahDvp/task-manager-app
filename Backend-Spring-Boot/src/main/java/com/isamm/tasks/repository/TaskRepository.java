package com.isamm.tasks.repository;

import com.isamm.tasks.models.Label;
import com.isamm.tasks.models.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;


// Repository interface for managing tasks in the database.
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

	// Retrieve tasks by project ID.
	List<Task> findByProjectId(Long projectId);

	// Retrieve tasks by label ID using a JPQL query.
	@Query("SELECT t FROM Task t JOIN t.labels l WHERE l.id = :labelId")
	List<Task> findByLabelId(@Param("labelId") Long labelId);

	// Retrieve tasks with due dates before a specified date.
	List<Task> findByDueDateBefore(LocalDate date);

	// Retrieve tasks with due dates after a specified date.
	List<Task> findByDueDateAfter(LocalDate date);

	// Retrieve tasks based on a search term using a JPQL query.
	@Query("SELECT t FROM Task t " +
			"LEFT JOIN t.labels l " +
			"WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
			"LOWER(t.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
			"LOWER(t.priority) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
			"LOWER(t.status) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
			"LOWER(t.project.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
			"LOWER(l.name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
	Page<Task> findBySearchTerm(@Param("searchTerm") String searchTerm, Pageable pageable);

	// Retrieve archived tasks using a JPQL query.
	@Query("SELECT t FROM Task t WHERE t.status = 'ARCHIVED'")
	Page<Task> findAllArchived(Pageable pageable);

	// Retrieve tasks by member ID and project ID using a JPQL query.
	@Query("SELECT t FROM Task t JOIN t.project p JOIN t.members m WHERE m.id = :memberId AND p.id = :projectId")
	List<Task> findTasksByMemberIdAndProjectId(@Param("memberId") Long memberId, @Param("projectId") Long projectId);

	// Retrieve paginated tasks by member ID using a JPQL query.
	@Query("SELECT t FROM Task t JOIN t.members m WHERE m.id = :memberId")
	Page<Task> findTasksByMemberId(Pageable pageable, @Param("memberId") Long memberId);
}
