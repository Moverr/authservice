package com.kodeinc.authservice.services.impl;

import com.kodeinc.authservice.exceptions.CustomBadRequestException;
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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Muyinda Rogers
 * @Date 2023-12-13
 * @Email moverr@gmail.com
 */
@Slf4j
@Service
public class ProjectResourceServiceImpl  extends BaseServiceImpl implements ProjectResourceService {

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
        return null;
    }

    /**
     * @param httpServletRequest
     * @param id
     * @return
     */
    @Override
    public ProjectResourceResponse getByID(HttpServletRequest httpServletRequest, long id) {
        return null;
    }

    /**
     * @param httpServletRequest
     * @param query
     * @return
     */
    @Override
    public CustomPage<ProjectResourceResponse> list(HttpServletRequest httpServletRequest, SearchRequest query) {
        return null;
    }

    /**
     * @param httpServletRequest
     * @param id
     */
    @Override
    public void delete(HttpServletRequest httpServletRequest, long id) {

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


    private ProjectResource populate(ProjectResourceRequest request){
        Project project =  projectRepository.getReferenceById(request.getProjectId());
        ProjectResource projectResource = new ProjectResource();
        projectResource.setProject(project);
        projectResource.setName(request.getName());
        return projectResource;

    }

    private ProjectResourceResponse populate(ProjectResource request){
   return ProjectResourceResponse
                .builder()
                .id(request.getId())
                .name(request.getName())
                .build();

    }



}
