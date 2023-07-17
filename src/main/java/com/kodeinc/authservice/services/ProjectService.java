package com.kodeinc.authservice.services;

import com.kodeinc.authservice.models.dtos.requests.ProjectRequestDTO;
import com.kodeinc.authservice.models.dtos.requests.SearchRequestDTO;
import com.kodeinc.authservice.models.dtos.responses.CustomPage;
import com.kodeinc.authservice.models.dtos.responses.ProjectResponseDTO;
import com.kodeinc.authservice.models.entities.Project;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface ProjectService extends BasicService<ProjectRequestDTO, ProjectResponseDTO, Project> {


     @Override
     ProjectResponseDTO create(ProjectRequestDTO request);
     @Override
      ProjectResponseDTO update(long id,ProjectRequestDTO request);
     @Override
      CustomPage<ProjectResponseDTO> list(SearchRequestDTO query);
     @Override
      Optional<ProjectResponseDTO> getByID(long id);
     @Override
      void delete(long id);


}
