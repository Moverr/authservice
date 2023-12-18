package com.kodeinc.authservice.services;

import com.kodeinc.authservice.models.dtos.requests.PermissionRequest;
import com.kodeinc.authservice.models.dtos.requests.PermissionSearchRequest;
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
 * @Date 2023-12-13
 * @Email moverr@gmail.com
 */
public interface PermissionService {

    PermissionResponse create(HttpServletRequest httpServletRequest, PermissionRequest request);
    PermissionResponse update(HttpServletRequest httpServletRequest, long id, PermissionRequest request);
    PermissionResponse getByID(HttpServletRequest httpServletRequest, long id);
    CustomPage<PermissionResponse> list(HttpServletRequest httpServletRequest, PermissionSearchRequest query);

    void delete(HttpServletRequest httpServletRequest, long id);
    Set<Permission> findPermissions(List<Long> permissionIds);
    PermissionResponse populate(Permission entity);


}
