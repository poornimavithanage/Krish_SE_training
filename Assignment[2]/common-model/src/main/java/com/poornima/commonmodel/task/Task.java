package com.poornima.commonmodel.task;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "task")
@Data
public class Task {

    @Id
    private String taskId;
    private int projectId;
    private String projectName;
    private String taskName;
    private String assignedId;
    private String assignedName;
}
