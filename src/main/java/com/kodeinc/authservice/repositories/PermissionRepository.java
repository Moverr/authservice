package com.kodeinc.authservice.repositories;

import com.kodeinc.authservice.models.entities.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PermissionRepository extends JpaRepository<Permission,Long> {

    Page<Permission> findAllByCreatedBy(long createdBy, Pageable pageable);

    @Query(value = "SELECT U from Permission U where U.resource_id =:resourceId and U.created_by=:createdBy",nativeQuery = true)
    Page<Permission> findAllByCreatedByAndResource(long createdBy,long resourceId, Pageable pageable);

    @Query(value = "SELECT U from Permission U where U.resource_id =:resourceId ",nativeQuery = true)
    Page<Permission> findAllByResource(long resourceId, Pageable pageable);


}
