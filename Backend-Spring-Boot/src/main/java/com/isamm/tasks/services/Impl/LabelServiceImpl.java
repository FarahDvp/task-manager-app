package com.isamm.tasks.services.Impl;

import com.isamm.tasks.dto.LabelDTO;
import com.isamm.tasks.mapper.LabelMapper;
import com.isamm.tasks.models.Label;
import com.isamm.tasks.repository.LabelRepository;
import com.isamm.tasks.services.LabelService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
// Service class for managing Label entities.
@Service
@Transactional
public class LabelServiceImpl implements LabelService {

    // Autowired LabelRepository for interacting with the Label database.
    @Autowired
    private LabelRepository labelRepository;

    // Autowired LabelMapper for mapping between LabelDTO and Label entities.
    @Autowired
    private LabelMapper labelMapper;

    // Save a new label or update an existing one.
    @Override
    public LabelDTO save(LabelDTO labelDTO) {
        // Convert LabelDTO to Label entity and save it to the repository.
        Label label = labelMapper.toEntity(labelDTO);
        label = labelRepository.save(label);
        // Convert the saved Label entity back to LabelDTO and return it.
        return labelMapper.toDto(label);
    }

    // Retrieve all labels.
    @Override
    public List<LabelDTO> findAll() {
        // Convert the list of Label entities to a list of LabelDTOs and return it.
        return labelMapper.toDto(labelRepository.findAll());
    }

    // Retrieve a label by its ID.
    @Override
    public LabelDTO findOne(Long id) {
        // Find the Label entity by ID in the repository.
        Optional<Label> label = labelRepository.findById(id);
        // Convert the found Label entity to LabelDTO and return it.
        return labelMapper.toDto(label.orElse(null));
    }

    // Delete a label by its ID.
    @Override
    public void delete(Long id) {
        // Delete the Label entity from the repository by ID.
        labelRepository.deleteById(id);
    }
}
