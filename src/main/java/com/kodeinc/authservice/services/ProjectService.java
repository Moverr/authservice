package com.kodeinc.authservice.services;

import com.kodeinc.authservice.models.dtos.requests.ProjectRequest;
import com.kodeinc.authservice.models.dtos.requests.SearchRequest;
import com.kodeinc.authservice.models.dtos.responses.CustomPage;
import com.kodeinc.authservice.models.dtos.responses.ProjectResponse;
import com.kodeinc.authservice.models.entities.Project;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Set;

/**
 * @author Muyinda Rogers
 * @Date 2023-12-02
 * @Email moverr@gmail.com
 */
public interface ProjectService {
    ProjectResponse create(HttpServletRequest httpServletRequest, ProjectRequest request);
    ProjectResponse update(HttpServletRequest httpServletRequest, long id, ProjectRequest request);

    ProjectResponse getByID(HttpServletRequest httpServletRequest, long id);

    CustomPage<ProjectResponse> list(HttpServletRequest httpServletRequest, SearchRequest query);
    void  delete(HttpServletRequest httpServletRequest,long id);
    Set<Project> findProjects(List<Long> projectIds);
    Project populate(ProjectRequest request) ;
    ProjectResponse populate(Project entity);


}

