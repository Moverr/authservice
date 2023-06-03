package com.kodeinc.authservice.repositories;

import com.kodeinc.authservice.models.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
}
