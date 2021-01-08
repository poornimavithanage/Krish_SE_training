package com.poornima.project.projectservice.repository;

import com.poornima.commonmodel.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project,Integer> {
    Optional<Project> findById(int id);
}
