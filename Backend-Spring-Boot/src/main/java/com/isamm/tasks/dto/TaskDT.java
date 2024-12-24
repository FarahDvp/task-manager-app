package com.isamm.tasks.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TaskDT {
    private int draw = 1;
    private Long recordsTotal;
    private Long recordsFiltered;
    private List<TaskDTO> data = new ArrayList<>();

}
