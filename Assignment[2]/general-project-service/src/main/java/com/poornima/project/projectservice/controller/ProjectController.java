package com.poornima.project.projectservice.controller;

import com.poornima.commonmodel.project.Project;

import com.poornima.project.projectservice.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RestController
public class ProjectController {
    @Autowired
    ProjectService projectService;

    @RequestMapping(value = "/project",method = RequestMethod.POST)
    public Project save(@RequestBody Project project){
        return projectService.save(project);

    }

    @RequestMapping(value = "/project",method =RequestMethod.GET)
    public ResponseEntity<Project> fetchProject(@RequestParam int id) {
        Project project = projectService.fetchProjectById(id);
        if(project == null){
            return  ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok().body(project);

        }

    }


    @RequestMapping(value = "/project/all" , method = RequestMethod.GET)
    public List<Project> getAllProject(){
        log.info("getting all project");
        return projectService.getAllProjects();
    }

    @RequestMapping(value = "/project",method = RequestMethod.PUT)
    public ResponseEntity update(@RequestBody Project project){
        projectService.update(project);
        return ResponseEntity.ok().body(project);
    }


    @RequestMapping(value = "/project",method =RequestMethod.DELETE)
    public ResponseEntity<String> delete(@RequestParam int id) {
        try {
            projectService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Project is deleted successfully");
        } catch (NoSuchElementException noSuchElementException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project Id does not exist");
        }

    }

}
