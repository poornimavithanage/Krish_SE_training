package com.poornima.project.projectservice.controller;

import com.google.gson.Gson;
import com.poornima.commonmodel.project.Project;

import com.poornima.project.projectservice.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

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
    public Project update(@RequestBody Project project){
        try {
            return projectService.update(project);
        } catch (NullPointerException e) {
            throw new NullPointerException("Invalid project ID");
        }
    }

    @RequestMapping(value = "/project",method =RequestMethod.DELETE)
    public ResponseEntity<Project>delete(@RequestParam int id) {
        Project project = projectService.delete(id);
        try {
            if(project == null){
                return  ResponseEntity.notFound().build();
            }else{
                return ResponseEntity.ok().body(project);
            }
        } catch (NullPointerException e) {
            throw new NullPointerException("Invalid project ID");
        }
    }

}
