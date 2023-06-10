package com.kodeinc.authservice.services.impl;

import com.kodeinc.authservice.models.dtos.requests.ProjectRequestDTO;
import com.kodeinc.authservice.models.dtos.requests.SearchRequestDTO;
import com.kodeinc.authservice.models.dtos.responses.ProjectResponseDTO;
import com.kodeinc.authservice.services.ProjectService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Override
    public ProjectResponseDTO create(ProjectRequestDTO request) {
        return null;
    }

    @Override
    public ProjectResponseDTO update(long id, ProjectRequestDTO request) {
        return null;
    }

    @Override
    public Page<ProjectResponseDTO> list(SearchRequestDTO query) {
        return null;
    }

    @Override
    public void archive(long id) {

    }
}
