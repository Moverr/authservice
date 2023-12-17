package com.kodeinc.authservice.repositories;

import com.kodeinc.authservice.models.entities.Permission;
import com.kodeinc.authservice.models.entities.ProjectResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PermissionRepository extends JpaRepository<Permission,Long> {

    Page<Permission> findAllByCreatedBy(long createdBy, Pageable pageable);

    @Query(value = "SELECT U from Permission U where  U.created_by = :createdBy and U.resource = :pr")
    Page<Permission> findAllByCreatedByAndResource(long createdBy, ProjectResource pr, Pageable pageable);

    @Query(value = "SELECT U from Permission U  U.resource = :pr ")
    Page<Permission> findAllByResource(ProjectResource pr, Pageable pageable);


}
