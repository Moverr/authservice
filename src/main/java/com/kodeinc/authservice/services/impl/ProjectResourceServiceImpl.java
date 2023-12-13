package com.kodeinc.authservice.services.impl;

import com.kodeinc.authservice.exceptions.CustomBadRequestException;
import com.kodeinc.authservice.exceptions.CustomNotFoundException;
import com.kodeinc.authservice.exceptions.KhoodiUnAuthroizedException;
import com.kodeinc.authservice.models.dtos.requests.ProjectResourceRequest;
import com.kodeinc.authservice.models.dtos.requests.SearchRequest;
import com.kodeinc.authservice.models.dtos.responses.AuthorizeRequestResponse;
import com.kodeinc.authservice.models.dtos.responses.CustomPage;
import com.kodeinc.authservice.models.dtos.responses.PermissionResponse;
import com.kodeinc.authservice.models.dtos.responses.ProjectResourceResponse;
import com.kodeinc.authservice.models.entities.Project;
import com.kodeinc.authservice.models.entities.ProjectResource;
import com.kodeinc.authservice.models.entities.entityenums.PermissionLevelEnum;
import com.kodeinc.authservice.repositories.ProjectRepository;
import com.kodeinc.authservice.repositories.ProjectResourceRepository;
import com.kodeinc.authservice.services.ProjectResourceService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Muyinda Rogers
 * @Date 2023-12-13
 * @Email moverr@gmail.com
 */
@Slf4j
@Service
public class ProjectResourceServiceImpl extends BaseServiceImpl implements ProjectResourceService {

    @Autowired
    private ProjectResourceRepository repository;

    @Autowired
    private ProjectRepository projectRepository;

    /**
     * @param httpServletRequest
     * @param request
     * @return
     */
    @Override
    public ProjectResourceResponse create(HttpServletRequest httpServletRequest, ProjectResourceRequest request) throws EntityNotFoundException {
        log.info("ProjectResourceServiceImpl   create method");

        AuthorizeRequestResponse authenticatedPermission = authorizeRequestPermissions(httpServletRequest, getPermission());

        if (authenticatedPermission.getPermission() != null && (authenticatedPermission.getPermission().getResource().equalsIgnoreCase("ALL_FUNCTIONS") || authenticatedPermission.getPermission().getCreate().equals(PermissionLevelEnum.FULL))) {
            List<ProjectResource> projectList = repository.findAllByNameAndProject(request.getName(), request.getProjectId());
            if (!projectList.isEmpty()) {
                throw new CustomBadRequestException("Project Resource Already Exists");
            }
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
    public ProjectResourceResponse update(HttpServletRequest httpServletRequest, long id, ProjectResourceRequest request) {

        ProjectResource projectResource = repository.findById(id).orElseThrow(() -> new CustomNotFoundException("Project Resource not found"));
        AuthorizeRequestResponse authResponse = authorizeRequestPermissions(httpServletRequest, getPermission());

        if (authResponse.getPermission() != null && (authResponse.getPermission().getResource().equalsIgnoreCase("ALL_FUNCTIONS") || authResponse.getPermission().getUpdate() != (PermissionLevelEnum.NONE))) {


            switch (authResponse.getPermission().getUpdate()) {
                case MINE -> {
                    if (projectResource.getCreatedBy() != authResponse.getAuth().getUser().getUserId()) {
                        throw new KhoodiUnAuthroizedException("You dont have permission to update projectResource resources");
                    }
                }
                case NONE -> {
                    throw new KhoodiUnAuthroizedException("You dont have permission to update projectResource resources");
                }
                case ROLE -> {
                    //todo: find if the user exists in the same role.
                }
                case FULL -> {
                    //todo: nothing to do here for now.
                }

            }

            ProjectResource newRecordUpdate = populate(projectResource, request);

            return this.populate(repository.save(newRecordUpdate));

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
    public ProjectResourceResponse getByID(HttpServletRequest httpServletRequest, long id) {

        AuthorizeRequestResponse authResponse = authorizeRequestPermissions(httpServletRequest, getPermission());
        if (authResponse.getPermission() != null && (authResponse.getPermission().getResource().equalsIgnoreCase("ALL_FUNCTIONS") || authResponse.getPermission().getRead() != (PermissionLevelEnum.NONE))) {
            ProjectResource projectResource = repository.findById(id).orElseThrow(() -> new CustomNotFoundException("Project Resource not found"));


            switch (authResponse.getPermission().getRead()) {
                case MINE -> {
                    if (projectResource.getCreatedBy() != authResponse.getAuth().getUser().getUserId()) {
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


            return populate(projectResource);

        } else
            throw new KhoodiUnAuthroizedException("You dont have permission to view this record");


    }

    /**
     * @param httpServletRequest
     * @param query
     * @return
     */
    @Override
    public CustomPage<ProjectResourceResponse> list(HttpServletRequest httpServletRequest, SearchRequest query) {

        Sort sort = switch (query.getSortBy()) {
            case "code" -> Sort.by("code");
            case "id" -> Sort.by("id");
            case "created_at" -> Sort.by("created_at");
            case "updated_at" -> Sort.by("updated_at");
            default -> Sort.by("name");
        };

        sort = switch (query.getSortType()) {
            case "asc" -> sort.ascending();
            default -> sort.descending();
        };
        AuthorizeRequestResponse authResponse = authorizeRequestPermissions(httpServletRequest, getPermission());
        if (authResponse.getPermission() != null && (authResponse.getPermission().getResource().equalsIgnoreCase("ALL_FUNCTIONS") || authResponse.getPermission().getRead() != (PermissionLevelEnum.NONE))) {

            Pageable pageable = PageRequest.of(query.getOffset(), query.getLimit(), sort);
            Page<ProjectResource> projectResources;

            switch (authResponse.getPermission().getRead()) {
                case MINE ->
                        projectResources = repository.findAllByCreatedBy(authResponse.getAuth().getUser().getUserId(), pageable);

                case NONE -> throw new KhoodiUnAuthroizedException("You dont have permission to view this record");

                case ROLE -> throw new RuntimeException("Not yet implemented role level");

                case FULL -> projectResources = repository.findAll(pageable);
                default -> throw new KhoodiUnAuthroizedException("You are not authorized");

            }

            List<ProjectResourceResponse> responses = projectResources.stream().map(this::populate).collect(Collectors.toList());

            return getCustomPage(projectResources, responses);

        } else
            throw new KhoodiUnAuthroizedException("You dont have permission to view");

    }



    /**
     * @param httpServletRequest
     * @param id
     * We are going to replace this with a safe delete option
     */
    @Deprecated
    @Override
    public void delete(HttpServletRequest httpServletRequest, long id) throws CustomNotFoundException{
        log.info("Deleting a record {} ",id);
        AuthorizeRequestResponse authResponse = authorizeRequestPermissions(httpServletRequest, getPermission());
        if (authResponse.getPermission() != null && (authResponse.getPermission().getResource().equalsIgnoreCase("ALL_FUNCTIONS") || authResponse.getPermission().getDelete()!= (PermissionLevelEnum.NONE))) {
            ProjectResource projectResource = repository.findById(id).orElseThrow(() -> new CustomNotFoundException("Record does not exist"));
            repository.delete(projectResource);
        }else{
            log.info("UnAuthorized Access {} ",httpServletRequest.toString());
            throw new KhoodiUnAuthroizedException("You dont have permission delete");

        }

    }

    private static CustomPage<ProjectResourceResponse> getCustomPage(Page<ProjectResource> projectResources, List<ProjectResourceResponse> responses) {
        CustomPage<ProjectResourceResponse> customResponse = new CustomPage<>();
        customResponse.setData(responses);
        customResponse.setPageNumber(projectResources.getNumber());
        customResponse.setPageSize(projectResources.getSize());
        customResponse.setPageNumber(projectResources.getNumber());
        customResponse.setTotalElements(projectResources.getTotalElements());
        return customResponse;
    }

    private static List<PermissionResponse> getPermission() {

        List<PermissionResponse> expectedPermissions = new ArrayList<>();
        PermissionResponse permissionResponse = new PermissionResponse();
        permissionResponse.setResource("ALL_FUNCTIONS");
        expectedPermissions.add(permissionResponse);
        permissionResponse = new PermissionResponse();
        permissionResponse.setResource("PROJECTS_RESOURCES");
        expectedPermissions.add(permissionResponse);
        return expectedPermissions;
    }


    private ProjectResource populate(ProjectResource entity, ProjectResourceRequest request) {
        entity.setName(request.getName().isBlank() ? entity.getName() : request.getName());
        return entity;
    }

    private ProjectResource populate(ProjectResourceRequest request) {
        Project project = projectRepository.getReferenceById(request.getProjectId());
        ProjectResource projectResource = new ProjectResource();
        projectResource.setProject(project);
        projectResource.setName(request.getName());
        return projectResource;
    }

    private ProjectResourceResponse populate(ProjectResource request) {
        return ProjectResourceResponse
                .builder()
                .id(request.getId())
                .name(request.getName())
                .build();
    }


}
