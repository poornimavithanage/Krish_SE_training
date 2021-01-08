package com.poornima.project.projectservice.service;

import com.poornima.commonmodel.project.Project;

import java.io.IOException;
import java.util.List;

public interface ProjectService {

    Project save (Project project) ;

    Project fetchProjectById(int id) ;

    Project update (Project project) ;

    Project delete(int id);

    List<Project> getAllProjects();
}
