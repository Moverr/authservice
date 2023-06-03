package com.kodeinc.authservice.repositories;

import com.kodeinc.authservice.models.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project,Long> {
}
