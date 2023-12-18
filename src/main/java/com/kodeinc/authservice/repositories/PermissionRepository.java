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

    @Query(value = "SELECT U from permissions U   INNER JOIN project_resources B ON U.resource_id = B.id where  U.created_by = :createdBy and U.resource_id = :pr",nativeQuery = true)
    Page<Permission> findAllByCreatedByAndResource(@Param("createdBy") long createdBy,@Param("pr")  long pr, Pageable pageable);

    @Query(value = "SELECT U.* from permissions  U  INNER JOIN project_resources B ON U.resource_id = B.id where U.resource_id = :pr ",nativeQuery = true)
    Page<Permission> findAllByResource(@Param("pr")  long pr, Pageable pageable);


}
