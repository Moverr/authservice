package com.kodeinc.authservice.repositories;

import com.kodeinc.authservice.models.entities.ProjectResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
    @Query(value = "SELECT u FROM ProjectResource u  where u.project_id = :projectId and u.resource like :name ",nativeQuery = true)
    List<ProjectResource> findResourcesByNameandProject(String name, long projectId);


    @Transactional(readOnly = true)
    Page<ProjectResource> findAllByCreatedBy(long createdBy, Pageable pageable);



}
