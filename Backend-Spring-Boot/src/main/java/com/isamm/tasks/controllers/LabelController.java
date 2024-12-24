package com.isamm.tasks.controllers;

import com.isamm.tasks.dto.LabelDTO;
import com.isamm.tasks.services.LabelService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@SecurityRequirement(name = "bearer-token")
@RequestMapping("/api/labels")
public class LabelController {

    @Autowired
    private LabelService labelService;

    // Create a new label
    @PostMapping
    public ResponseEntity<LabelDTO> create(@RequestBody LabelDTO labelDTO) {
        // Save the label using LabelService and return the created label
        LabelDTO label = labelService.save(labelDTO);
        return ResponseEntity.ok().body(label);
    }

    // Get all labels
    @GetMapping
    public ResponseEntity<List<LabelDTO>> findAll() {
        // Retrieve all labels using LabelService and return the list
        List<LabelDTO> labels = labelService.findAll();
        return ResponseEntity.ok().body(labels);
    }

    // Get a specific label by ID
    @GetMapping("/{id}")
    public ResponseEntity<LabelDTO> findOne(@PathVariable Long id) {
        // Retrieve a label by ID using LabelService and return it
        LabelDTO label = labelService.findOne(id);
        return ResponseEntity.ok().body(label);
    }

    // Delete a label by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // Delete a label by ID using LabelService
        labelService.delete(id);
        return ResponseEntity.ok().build();
    }
}
