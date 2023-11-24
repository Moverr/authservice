package com.kodeinc.authservice.repositories;

import com.kodeinc.authservice.models.entities.ProjectResource;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Muyinda Rogers
 * @Date 2023-09-17
 * @Email moverr@gmail.com
 */
public interface ResourceRepository extends JpaRepository<ProjectResource,Long> {
}
