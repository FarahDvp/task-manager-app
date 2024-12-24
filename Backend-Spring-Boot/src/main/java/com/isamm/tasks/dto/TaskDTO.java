package com.isamm.tasks.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    private Long id;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate dueDate;
    private boolean completed;
    private List<LabelDTO> labels;
    private Long projectId;
    private String projectName;
    private List<MemberDTO> members;
    private String status;
    private String priority;
}
