package com.kodeinc.authservice.services.impl;

import com.kodeinc.authservice.exceptions.CustomNotFoundException;
import com.kodeinc.authservice.exceptions.KhoodiUnAuthroizedException;
import com.kodeinc.authservice.models.dtos.requests.PermissionRequest;
import com.kodeinc.authservice.models.dtos.requests.ProjectResourceRequest;
import com.kodeinc.authservice.models.dtos.requests.SearchRequest;
import com.kodeinc.authservice.models.dtos.responses.AuthorizeRequestResponse;
import com.kodeinc.authservice.models.dtos.responses.CustomPage;
import com.kodeinc.authservice.models.dtos.responses.PermissionResponse;
import com.kodeinc.authservice.models.dtos.responses.ProjectResourceResponse;
import com.kodeinc.authservice.models.entities.Permission;
import com.kodeinc.authservice.models.entities.ProjectResource;
import com.kodeinc.authservice.models.entities.entityenums.PermissionLevelEnum;
import com.kodeinc.authservice.repositories.PermissionRepository;
import com.kodeinc.authservice.repositories.ProjectResourceRepository;
import com.kodeinc.authservice.services.PermissionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

        Permission permission = repository.findById(id).orElseThrow(() -> new CustomNotFoundException("Project Resource not found"));
        AuthorizeRequestResponse authResponse = authorizeRequestPermissions(httpServletRequest, getPermission());

        if (authResponse.getPermission() != null && (authResponse.getPermission().getResource().equalsIgnoreCase("ALL_FUNCTIONS") || authResponse.getPermission().getUpdate() != (PermissionLevelEnum.NONE))) {

            switch (authResponse.getPermission().getUpdate()) {
                case MINE -> {
                    if (permission.getCreatedBy() != authResponse.getAuth().getUser().getUserId()) {
                        throw new KhoodiUnAuthroizedException("You dont have permission to update permission resources");
                    }
                }
                case NONE ->
                        throw new KhoodiUnAuthroizedException("You dont have permission to update permission resources");

                case ROLE -> {
                    //todo: find if the user exists in the same role.
                }
                case FULL -> {
                    //todo: nothing to do here for now.
                }
            }

            Permission newPermission = populate(permission, request);
            return this.populate(repository.save(newPermission));

        } else {
            throw new KhoodiUnAuthroizedException("You dont have permission to update projects");
        }

    }

    /**
     * @param httpServletRequest
     * @param id
     * @return
     */
    @Override
    public PermissionResponse getByID(HttpServletRequest httpServletRequest, long id) {

        AuthorizeRequestResponse authResponse = authorizeRequestPermissions(httpServletRequest, getPermission());
        if (authResponse.getPermission() != null && (authResponse.getPermission().getResource().equalsIgnoreCase("ALL_FUNCTIONS") || authResponse.getPermission().getRead() != (PermissionLevelEnum.NONE))) {
            Permission permission = repository.findById(id).orElseThrow(() -> new CustomNotFoundException("Project Resource not found"));


            switch (authResponse.getPermission().getRead()) {
                case MINE -> {
                    if (permission.getCreatedBy() != authResponse.getAuth().getUser().getUserId()) {
                        throw new KhoodiUnAuthroizedException("You dont have permission to view this record");
                    }
                }
                case NONE -> throw new KhoodiUnAuthroizedException("You dont have permission to view this record");

                case ROLE -> {
                    //todo: find if the user exists in the same role.
                }
                case FULL -> {
                    //todo: nothing to do here for now.
                }

            }

            return populate(permission);

        } else
            throw new KhoodiUnAuthroizedException("You dont have permission to view this record");

    }

    /**
     * @param httpServletRequest
     * @param query
     * @return
     */
    @Override
    public CustomPage<PermissionResponse> list(HttpServletRequest httpServletRequest, SearchRequest query) {

        Sort sort = switch (query.getSortBy()) {
            case "code" -> Sort.by("code");
            case "id" -> Sort.by("id");
            case "created_at" -> Sort.by("created_at");
            case "updated_at" -> Sort.by("updated_at");
            default -> Sort.by("id");
        };

        sort = switch (query.getSortType()) {
            case "asc" -> sort.ascending();
            default -> sort.descending();
        };

        AuthorizeRequestResponse authResponse = authorizeRequestPermissions(httpServletRequest, getPermission());
        if (authResponse.getPermission() != null && (authResponse.getPermission().getResource().equalsIgnoreCase("ALL_FUNCTIONS") || authResponse.getPermission().getRead() != (PermissionLevelEnum.NONE))) {

            Pageable pageable = PageRequest.of(query.getOffset(), query.getLimit(), sort);
            Page<Permission> permissionPage;

            switch (authResponse.getPermission().getRead()) {
                case MINE ->
                        permissionPage = repository.findAllByCreatedBy(authResponse.getAuth().getUser().getUserId(), pageable);

                case NONE -> throw new KhoodiUnAuthroizedException("You dont have permission to view this record");

                case ROLE -> throw new RuntimeException("Not yet implemented role level");

                case FULL -> permissionPage = repository.findAll(pageable);
                default -> throw new KhoodiUnAuthroizedException("You are not authorized");

            }

            List<PermissionResponse> responses = permissionPage.stream().map(this::populate).collect(Collectors.toList());

            return getCustomPage(permissionPage, responses);

        } else
            throw new KhoodiUnAuthroizedException("You dont have permission to view");


    }


    private static CustomPage<PermissionResponse> getCustomPage(Page<Permission> projectResources, List<PermissionResponse> responses) {
        CustomPage<PermissionResponse> customResponse = new CustomPage<>();
        customResponse.setData(responses);
        customResponse.setPageNumber(projectResources.getNumber());
        customResponse.setPageSize(projectResources.getSize());
        customResponse.setPageNumber(projectResources.getNumber());
        customResponse.setTotalElements(projectResources.getTotalElements());
        return customResponse;
    }



    /**
     * @param httpServletRequest
     * @param id
     */
    @Override
    public void delete(HttpServletRequest httpServletRequest, long id) {
        log.info("Deleting a record {} ",id);
        AuthorizeRequestResponse authResponse = authorizeRequestPermissions(httpServletRequest, getPermission());
        if (authResponse.getPermission() != null && (authResponse.getPermission().getResource().equalsIgnoreCase("ALL_FUNCTIONS") || authResponse.getPermission().getDelete()!= (PermissionLevelEnum.NONE))) {
            Permission permission = repository.findById(id).orElseThrow(() -> new CustomNotFoundException("Record does not exist"));
            repository.delete(permission);
        }else{
            log.info("UnAuthorized Access {} ",httpServletRequest.toString());
            throw new KhoodiUnAuthroizedException("You dont have permission delete");
        }
    }

    /**
     * @param permissionIds
     * @return
     */
    @Override
    public Set<Permission> findPermissions(List<Long> permissionIds) {
        return new HashSet<>(repository.findAllById(permissionIds));
    }

    /**
     * @param entity
     * @return
     */
    @Override
    public PermissionResponse populate(Permission entity) {

        PermissionResponse permissionResponse = new PermissionResponse();
        permissionResponse.setCreate(entity.getCreate());
        permissionResponse.setUpdate(entity.getUpdate());
        permissionResponse.setRead(entity.getRead());
        permissionResponse.setDelete(entity.getDelete());
        permissionResponse.setResource(entity.getResource().getName());

        return permissionResponse;
    }

    private Permission populate(Permission entity, PermissionRequest request) {
        if(request.getResourceID() > 0){
            ProjectResource projectResource = projectResourceRepository.findById(request.getResourceID()).orElseThrow(()->new CustomNotFoundException("Resource not found"));
            entity.setResource(projectResource);
        }

        entity.setComment(request.getComment().name().isBlank() ? entity.getComment() : request.getComment());
        entity.setCreate(request.getCreate().name().isBlank() ? entity.getCreate() : request.getCreate());
        entity.setRead(request.getRead().name().isBlank() ? entity.getRead() : request.getRead());
        entity.setUpdate(request.getUpdate().name().isBlank() ? entity.getUpdate() : request.getUpdate());
        entity.setDelete(request.getDelete().name().isBlank() ? entity.getDelete() : request.getDelete());

        return entity;
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

