package com.poornima.task.taskservice.service;

import com.poornima.commonmodel.task.Task;
import com.poornima.task.taskservice.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService{

    @Autowired
    TaskRepository taskRepository;
    @Autowired
    RestTemplate restTemplate;

    @Override
    public Task save( Task task) {
      return taskRepository.save(task);
    }

    @Override
    public Task fetchTaskById(String id) throws IOException {

    Optional<Task>task = taskRepository.findById(id);

    if(task.isPresent()){
        return task.get();
    }else {
        return null;
    }

    }

    @Override
    public Task update(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
}
