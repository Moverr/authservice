package com.kodeinc.authservice.services;

import com.kodeinc.authservice.models.entities.Role;

import java.util.List;
import java.util.Set;

/**
 * @author Muyinda Rogers
 * @Date 2023-11-28
 * @Email moverr@gmail.com
 */
public interface RoleService {
    Set<Role> findRoles(List<Long> roleIds);
}
