package com.kodeinc.authservice.services.impl;

import com.kodeinc.authservice.exceptions.CustomBadRequestException;
import com.kodeinc.authservice.exceptions.CustomNotFoundException;
import com.kodeinc.authservice.exceptions.KhoodiUnAuthroizedException;
import com.kodeinc.authservice.models.dtos.requests.PermissionRequest;
import com.kodeinc.authservice.models.dtos.requests.SearchRequest;
import com.kodeinc.authservice.models.dtos.responses.AuthorizeRequestResponse;
import com.kodeinc.authservice.models.dtos.responses.CustomPage;
import com.kodeinc.authservice.models.dtos.responses.PermissionResponse;
import com.kodeinc.authservice.models.entities.Permission;
import com.kodeinc.authservice.models.entities.Project;
import com.kodeinc.authservice.models.entities.ProjectResource;
import com.kodeinc.authservice.models.entities.entityenums.PermissionLevelEnum;
import com.kodeinc.authservice.repositories.PermissionRepository;
import com.kodeinc.authservice.repositories.ProjectResourceRepository;
import com.kodeinc.authservice.services.PermissionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Service
public class PermissionServiceImpl extends BaseServiceImpl implements PermissionService  {

    @Autowired
    private  PermissionRepository repository;

    private ProjectResourceRepository projectResourceRepository;

    /**
     * @param httpServletRequest
     * @param request
     * @return
     */
    @Override
    public PermissionResponse create(HttpServletRequest httpServletRequest, PermissionRequest request) {

        log.info("ProjectServiceImpl   create method");

        //todo: validate user access
        AuthorizeRequestResponse authenticatedPermission = authorizeRequestPermissions(httpServletRequest, getPermission());

        if (authenticatedPermission.getPermission() != null && (authenticatedPermission.getPermission().getResource().equalsIgnoreCase("ALL_FUNCTIONS") || authenticatedPermission.getPermission().getCreate().equals(PermissionLevelEnum.FULL))) {
            return populate(repository.save(populate(request)));
        } else {
            throw new KhoodiUnAuthroizedException("You dont have permission to create projects");
        }


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


    public Permission populate(PermissionRequest entity) {
        ProjectResource projectResource = projectResourceRepository.findById(entity.getResourceID()).orElseThrow(()->new CustomNotFoundException("Resource not found"));

        Permission permission = new Permission();
        permission.setComment(entity.getComment());
        permission.setCreate(entity.getCreate());
        permission.setRead(entity.getRead());
        permission.setUpdate(entity.getUpdate());
        permission.setDelete(entity.getDelete());
        permission.setResource(projectResource);
        return permission;
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

