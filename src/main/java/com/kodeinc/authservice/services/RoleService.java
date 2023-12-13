package com.kodeinc.authservice.services;

import com.kodeinc.authservice.models.dtos.requests.RoleRequest;
import com.kodeinc.authservice.models.dtos.requests.SearchRequest;
import com.kodeinc.authservice.models.dtos.responses.CustomPage;
import com.kodeinc.authservice.models.dtos.responses.PermissionResponse;
import com.kodeinc.authservice.models.dtos.responses.RoleResponse;
import com.kodeinc.authservice.models.dtos.responses.RoleResponseDTO;
import com.kodeinc.authservice.models.entities.Permission;
import com.kodeinc.authservice.models.entities.Role;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Set;

/**
 * @author Muyinda Rogers
 * @Date 2023-11-28
 * @Email moverr@gmail.com
 */
public interface RoleService {
     RoleResponseDTO create(HttpServletRequest httpServletRequest, RoleRequest request);
     RoleResponseDTO update(HttpServletRequest httpServletRequest, long id, RoleRequest request);
     RoleResponseDTO getByID(HttpServletRequest httpServletRequest, long id);
      CustomPage<RoleResponseDTO> list(HttpServletRequest httpServletRequest, SearchRequest query);

     void delete(HttpServletRequest httpServletRequest, long id);
      Set<Role> findRoles(List<Long> roleIds);
     RoleResponse populate(Role entity);
     PermissionResponse populate(Permission entity);



}
