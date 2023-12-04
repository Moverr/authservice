package com.kodeinc.authservice.services;

import com.kodeinc.authservice.models.dtos.requests.ProjectRequest;
import com.kodeinc.authservice.models.dtos.requests.SearchRequest;
import com.kodeinc.authservice.models.dtos.responses.CustomPage;
import com.kodeinc.authservice.models.dtos.responses.ProjectResponseDTO;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author Muyinda Rogers
 * @Date 2023-12-02
 * @Email moverr@gmail.com
 */
public interface ProjectService {
    ProjectResponseDTO create(HttpServletRequest httpServletRequest, ProjectRequest request);
    ProjectResponseDTO update(HttpServletRequest httpServletRequest,long id, ProjectRequest request);

    ProjectResponseDTO getByID(HttpServletRequest httpServletRequest,long id);

    CustomPage<ProjectResponseDTO> list(HttpServletRequest httpServletRequest, SearchRequest query);
    void  delete(HttpServletRequest httpServletRequest,long id);
}
