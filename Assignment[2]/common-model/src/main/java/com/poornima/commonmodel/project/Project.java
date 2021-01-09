package com.poornima.commonmodel.project;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "project")
@Data
public class Project {

@Id
    private int id;
    private String projectName;
    private String status;

}
