package com.poornima.task.taskservice.repository;

import com.poornima.commonmodel.project.Project;
import com.poornima.commonmodel.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task,String> {
    Optional<Task> findById(String id);



}
