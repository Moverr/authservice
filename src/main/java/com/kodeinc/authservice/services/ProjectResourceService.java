package com.kodeinc.authservice.services;

import com.kodeinc.authservice.models.dtos.requests.ProjectResourceRequest;
import com.kodeinc.authservice.models.dtos.requests.SearchRequest;
import com.kodeinc.authservice.models.dtos.responses.CustomPage;
import com.kodeinc.authservice.models.dtos.responses.ProjectResourceResponse;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author Muyinda Rogers
 * @Date 2023-12-13
 * @Email moverr@gmail.com
 */
public interface ProjectResourceService {
    ProjectResourceResponse create(HttpServletRequest httpServletRequest, ProjectResourceRequest request);
    ProjectResourceResponse update(HttpServletRequest httpServletRequest,long id, ProjectResourceRequest request);
    ProjectResourceResponse getByID(HttpServletRequest httpServletRequest,long id);
    CustomPage<ProjectResourceResponse> list(HttpServletRequest httpServletRequest, SearchRequest query);
    void  delete(HttpServletRequest httpServletRequest,long id);

}
