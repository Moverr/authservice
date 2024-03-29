package com.kodeinc.authservice.services.impl;

import com.kodeinc.authservice.exceptions.CustomBadRequestException;
import com.kodeinc.authservice.exceptions.CustomNotFoundException;
import com.kodeinc.authservice.exceptions.KhoodiUnAuthroizedException;
import com.kodeinc.authservice.models.dtos.requests.ProjectRequest;
import com.kodeinc.authservice.models.dtos.requests.SearchRequest;
import com.kodeinc.authservice.models.dtos.responses.*;
import com.kodeinc.authservice.models.entities.Project;
import com.kodeinc.authservice.models.entities.entityenums.PermissionLevelEnum;
import com.kodeinc.authservice.repositories.ProjectRepository;
import com.kodeinc.authservice.services.ProjectService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
class ProjectServiceImpl extends BaseServiceImpl implements ProjectService {



    @Autowired
    private ProjectRepository repository;

    @Transactional
    @Override
    public ProjectResponse create(HttpServletRequest httpServletRequest, ProjectRequest request) throws CustomBadRequestException {
        log.info("ProjectServiceImpl   create method");

        //todo: validate user access
        AuthorizeRequestResponse authenticatedPermission = authorizeRequestPermissions(httpServletRequest, getPermission());

        if (authenticatedPermission.getPermission() != null && (authenticatedPermission.getPermission().getResource().equalsIgnoreCase("ALL_FUNCTIONS") || authenticatedPermission.getPermission().getCreate().equals(PermissionLevelEnum.FULL))) {
            List<Project> projectList = repository.findAllByNameAndCode(request.getName(), request.getCode());
            if (!projectList.isEmpty()) {
                throw new CustomBadRequestException("Project Already Exists");
            }
            return populate(repository.save(populate(request)));
        } else {
            throw new KhoodiUnAuthroizedException("You dont have permission to create projects");
        }


    }


    @Transactional
    @Override
    public ProjectResponse update(HttpServletRequest httpServletRequest, long id, ProjectRequest request) {
        Optional<Project> optionalProject = repository.findById(id);
        if (optionalProject.isEmpty()) {
            throw new CustomNotFoundException("Record does not exist");
        }

        AuthorizeRequestResponse authResponse = authorizeRequestPermissions(httpServletRequest, getPermission());

        if (authResponse.getPermission() != null && (authResponse.getPermission().getResource().equalsIgnoreCase("ALL_FUNCTIONS") || authResponse.getPermission().getUpdate()!= (PermissionLevelEnum.NONE))) {


            List<Project> projectList = repository.findByNameAndCodeAndNotID(id, request.getName(), request.getCode());
            if (!projectList.isEmpty()) {
                throw new CustomBadRequestException("Project Already Exists");
            }
            Project project = optionalProject.get();

            switch (authResponse.getPermission().getUpdate()){
                case MINE -> {
                    if(project.getCreatedBy() != authResponse.getAuth().getUser().getUserId()){
                        throw new KhoodiUnAuthroizedException("You dont have permission to update projects");
                    }
                }
                case NONE -> {
                    throw new KhoodiUnAuthroizedException("You dont have permission to update projects");
                }
                case ROLE -> {
                //todo: find if the user exists in the same role.
                }
                case FULL -> {
                    //todo: nothing to do here for now.
                }

            }

            Project newRecordUpdate = populate(request);
            newRecordUpdate.setId(project.getId());
            return this.populate(repository.save(newRecordUpdate));

        }
        else {
            throw new KhoodiUnAuthroizedException("You dont have permission to update projects");
        }

    }

    @Override
    public ProjectResponse getByID(HttpServletRequest httpServletRequest, long id) {
        AuthorizeRequestResponse authResponse = authorizeRequestPermissions(httpServletRequest, getPermission());
        if (authResponse.getPermission() != null && (authResponse.getPermission().getResource().equalsIgnoreCase("ALL_FUNCTIONS") || authResponse.getPermission().getRead()!= (PermissionLevelEnum.NONE))) {
            Optional<Project> optionalProject = repository.findById(id);
            if (optionalProject.isEmpty()) {
                throw new CustomNotFoundException("Record does not exist");
            }

            switch (authResponse.getPermission().getRead()){
                case MINE -> {
                    if(optionalProject.get().getCreatedBy() != authResponse.getAuth().getUser().getUserId()){
                        throw new KhoodiUnAuthroizedException("You dont have permission to view this record");
                    }
                }
                case NONE ->
                    throw new KhoodiUnAuthroizedException("You dont have permission to view this record");

                case ROLE -> {
                    //todo: find if the user exists in the same role.
                }
                case FULL -> {
                    //todo: nothing to do here for now.
                }

            }


            return populate(optionalProject.get());

        }
        else {
            log.info("UnAuthorized Access {} ",httpServletRequest.toString());
            throw new KhoodiUnAuthroizedException("You dont have permission to view this record");
        }


    }

    @Override
    public CustomPage<ProjectResponse> list(HttpServletRequest httpServletRequest, SearchRequest query) {
        Sort sort = switch (query.getSortBy()) {
            case "code" -> Sort.by("code");
            default -> Sort.by("name");
        };

        sort = switch (query.getSortType()) {
            case "asc" -> sort.ascending();
            default -> sort.descending();
        };
        AuthorizeRequestResponse authResponse = authorizeRequestPermissions(httpServletRequest, getPermission());
        if (authResponse.getPermission() != null && (authResponse.getPermission().getResource().equalsIgnoreCase("ALL_FUNCTIONS") || authResponse.getPermission().getRead()!= (PermissionLevelEnum.NONE))) {

            Pageable pageable = PageRequest.of(query.getOffset(), query.getLimit(), sort);
            Page<Project> projects;

            switch (authResponse.getPermission().getRead()){
                case MINE ->
                     projects = repository.findAllByCreatedBy(authResponse.getAuth().getUser().getUserId(),pageable);


                case NONE ->
                        throw new KhoodiUnAuthroizedException("You dont have permission to view this record");

                case ROLE ->
                    throw new RuntimeException("Not yet implemented role level");


                case FULL ->
                    projects = repository.findAll(pageable);
                default ->
                        throw new KhoodiUnAuthroizedException("You are not authorized");

            }

            List<ProjectResponse> responses =  projects.stream().map(ProjectServiceImpl::populate).collect(Collectors.toList());

            return getCustomPage(projects, responses);

        }else
            throw new KhoodiUnAuthroizedException("You dont have permission to view");


    }

    @Override
    public void delete(HttpServletRequest httpServletRequest, long id) {
        AuthorizeRequestResponse authResponse = authorizeRequestPermissions(httpServletRequest, getPermission());
        if (authResponse.getPermission() != null && (authResponse.getPermission().getResource().equalsIgnoreCase("ALL_FUNCTIONS") || authResponse.getPermission().getDelete()!= (PermissionLevelEnum.NONE))) {
            Project  project = repository.findById(id).orElseThrow(()-> new CustomNotFoundException("Record does not exist"));
            repository.delete(project);
        }else
            throw new KhoodiUnAuthroizedException("You dont have permission to view");

    }

    @Override
    public Set<Project> findProjects(List<Long> projectIds){
        log.info("Find Projects Method");
        return projectIds ==  null ? null :
         new HashSet<>(repository.findAllById(projectIds));
    }






    private static List<PermissionResponse> getPermission() {

        List<PermissionResponse> expectedPermissions = new ArrayList<>();
        PermissionResponse permissionResponse = new PermissionResponse();
        permissionResponse.setResource("ALL_FUNCTIONS");
        expectedPermissions.add(permissionResponse);
        permissionResponse = new PermissionResponse();
        //todo: if some one has permission projects, and also de
        permissionResponse.setResource("PROJECTS");
        expectedPermissions.add(permissionResponse);
        return expectedPermissions;

    }


    @Override
    public Project populate(ProjectRequest request) {
        Project entity = new Project();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setCallbackUrl(request.getCallbackUrl());
        return entity;
    }



    public static ProjectResponse populate(Project entity) {
        return ProjectResponse
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .code(entity.getCode())
                .callbackUrl(entity.getCallbackUrl())
                .build();

    }


}
