package com.isamm.tasks.models;

import lombok.Data;

@Data
public class TaskStatistics {
    private final long done;
    private final long delayed;
    private final long all;


}
