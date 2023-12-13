package com.kodeinc.authservice.services.impl;

import com.kodeinc.authservice.models.dtos.requests.PermissionRequest;
import com.kodeinc.authservice.models.dtos.requests.SearchRequest;
import com.kodeinc.authservice.models.dtos.responses.CustomPage;
import com.kodeinc.authservice.models.dtos.responses.PermissionResponse;
import com.kodeinc.authservice.models.entities.Permission;
import com.kodeinc.authservice.repositories.PermissionRepository;
import com.kodeinc.authservice.services.PermissionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Muyinda Rogers
 * @Date 2023-12-14
 * @Email moverr@gmail.com
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private  PermissionRepository permissionRepository;

    /**
     * @param httpServletRequest
     * @param request
     * @return
     */
    @Override
    public PermissionResponse create(HttpServletRequest httpServletRequest, PermissionRequest request) {
        return null;
    }

    /**
     * @param httpServletRequest
     * @param id
     * @param request
     * @return
     */
    @Override
    public PermissionResponse update(HttpServletRequest httpServletRequest, long id, PermissionRequest request) {
        return null;
    }

    /**
     * @param httpServletRequest
     * @param id
     * @return
     */
    @Override
    public PermissionResponse getByID(HttpServletRequest httpServletRequest, long id) {
        return null;
    }

    /**
     * @param httpServletRequest
     * @param query
     * @return
     */
    @Override
    public CustomPage<PermissionResponse> list(HttpServletRequest httpServletRequest, SearchRequest query) {
        return null;
    }

    /**
     * @param httpServletRequest
     * @param id
     */
    @Override
    public void delete(HttpServletRequest httpServletRequest, long id) {

    }

    /**
     * @param permissionIds
     * @return
     */
    @Override
    public Set<Permission> findPermissions(List<Long> permissionIds) {
        return null;
    }

    /**
     * @param entity
     * @return
     */
    @Override
    public PermissionResponse populate(Permission entity) {
        return null;
    }


    private static List<PermissionResponse> getPermission() {
        List<PermissionResponse> expectedPermissions = new ArrayList<>();
        PermissionResponse permissionResponse = new PermissionResponse();
        permissionResponse.setResource("ALL_FUNCTIONS");
        expectedPermissions.add(permissionResponse);
        permissionResponse = new PermissionResponse();
        permissionResponse.setResource("PERMISSIONS");
        expectedPermissions.add(permissionResponse);
        return expectedPermissions;
    }



}

