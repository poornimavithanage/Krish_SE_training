package com.poornima.task.taskservice.controller;

import com.google.gson.Gson;
import com.poornima.commonmodel.project.Project;
import com.poornima.commonmodel.task.Task;
import com.poornima.task.taskservice.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/tasks",method = RequestMethod.POST)
    public ResponseEntity save(@RequestBody Task task) {
        Project project = restTemplate.getForObject("http://localhost:8081/project?id="+task.getProjectId(),Project.class);
        String projectStatus = project.getStatus();
        task.setProjectName(project.getProjectName());

        try {
            if(projectStatus.equals("active")){
                return ResponseEntity.ok().body(task);
            }else{
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Can not assign tasks due to deactivate project.");
            }

        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Project id does not exists");
        }
    }

    @RequestMapping(value = "/tasks",method =RequestMethod.GET)
    public ResponseEntity<String> fetchTask(@RequestParam String id){
        Task task = null;
        try {
            task = taskService.fetchTaskById(id);

            if(task == null){
                return  ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body("Invalid task id"+id);
            }else{
                return ResponseEntity.ok().body(new Gson().toJson(task));
            }

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }


    @RequestMapping(value = "/tasks/all" , method = RequestMethod.GET)
    public List<Task>getAllTask(){
        try {
            return taskService.getAllTasks();
        } catch (NullPointerException e) {
            throw new NullPointerException("Invalid URL");
        }
    }

    @RequestMapping(value = "/tasks",method = RequestMethod.PUT)
    public ResponseEntity update(@RequestBody Task task){
        Project project = restTemplate.getForObject("http://localhost:8081/project?id="+task.getProjectId(),Project.class);
        String projectStatus = project.getStatus();
        try {
            if(projectStatus.equals("active")){
                return ResponseEntity.ok().body(task);
            }else{
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Can not update tasks due to deactivate project.");
            }

        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Project id does not exists");
        }
    }


}
