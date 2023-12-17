package com.kodeinc.authservice.repositories;

import com.kodeinc.authservice.models.entities.Permission;
import com.kodeinc.authservice.models.entities.ProjectResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission,Long> {

    Page<Permission> findAllByCreatedBy(long createdBy, Pageable pageable);
    Page<Permission> findAllByCreatedByAndResource(long createdBy,long resourceId, Pageable pageable);

}
