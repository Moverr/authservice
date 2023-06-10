package com.kodeinc.authservice.services;

import com.kodeinc.authservice.models.dtos.requests.CustomQueryDTO;
import com.kodeinc.authservice.models.dtos.requests.ProjectRequestDTO;
import com.kodeinc.authservice.models.dtos.responses.ProjectResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public interface ProjectService {



    //todo: create
    public ProjectResponseDTO create(ProjectRequestDTO request);
    //todo: update
    public ProjectResponseDTO update(long id,ProjectRequestDTO request);
    //todo: list
    public Page<ProjectResponseDTO> list(CustomQueryDTO query);
    //todo: delete
    public void archive(long id);


}
