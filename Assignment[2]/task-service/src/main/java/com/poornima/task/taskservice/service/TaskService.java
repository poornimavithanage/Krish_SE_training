package com.poornima.task.taskservice.service;


import com.poornima.commonmodel.task.Task;


import java.io.IOException;
import java.util.List;

public interface TaskService {
    Task save (Task task) ;

    Task fetchTaskById(String id) throws IOException;

    Task update (Task task) ;

    List<Task> getAllTasks() ;
}
