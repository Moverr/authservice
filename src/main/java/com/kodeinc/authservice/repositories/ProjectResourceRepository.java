package com.kodeinc.authservice.repositories;

import com.kodeinc.authservice.models.entities.ProjectResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Muyinda Rogers
 * @Date 2023-09-17
 * @Email moverr@gmail.com
 */
@Repository
public interface ProjectResourceRepository extends JpaRepository<ProjectResource,Long> {

    @Transactional(readOnly=true)
    List<ProjectResource> findAllByNameAndProject(String name, long projectId);


    Page<ProjectResource> findAllByCreatedBy(long createdBy, Pageable pageable);



}
