package com.isamm.tasks.repository;

import com.isamm.tasks.models.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


// Repository interface for managing labels in the database.
@Repository
public interface LabelRepository extends JpaRepository<Label, Long> {

    // Retrieve labels by name using a custom query.
    List<Label> findByName(String name);
}
