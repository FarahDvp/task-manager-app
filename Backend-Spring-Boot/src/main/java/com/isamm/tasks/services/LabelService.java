package com.isamm.tasks.services;

import com.isamm.tasks.dto.LabelDTO;

import java.util.List;

// Interface defining the contract for managing labels.
public interface LabelService {

    // Save a new label or update an existing one.
    LabelDTO save(LabelDTO labelDTO);

    // Retrieve all labels.
    List<LabelDTO> findAll();

    // Retrieve a label by its ID.
    LabelDTO findOne(Long id);

    // Delete a label by its ID.
    void delete(Long id);
}
