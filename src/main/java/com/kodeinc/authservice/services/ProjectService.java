package com.kodeinc.authservice.services;

import com.kodeinc.authservice.models.dtos.requests.ProjectRequestDTO;
import com.kodeinc.authservice.models.dtos.requests.SearchRequestDTO;
import com.kodeinc.authservice.models.dtos.responses.CustomPage;
import com.kodeinc.authservice.models.dtos.responses.ProjectResponseDTO;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface ProjectService {


      ProjectResponseDTO create(ProjectRequestDTO request);
      ProjectResponseDTO update(long id,ProjectRequestDTO request);
      CustomPage<ProjectResponseDTO> list(SearchRequestDTO query);
      Optional<ProjectResponseDTO> getByID(long id);
      void delete(long id);


}
