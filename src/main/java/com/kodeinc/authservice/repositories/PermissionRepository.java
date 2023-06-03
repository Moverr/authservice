package com.kodeinc.authservice.repositories;

import com.kodeinc.authservice.models.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission,Long> {
}
