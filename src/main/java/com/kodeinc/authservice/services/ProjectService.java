package com.kodeinc.authservice.services;

import com.kodeinc.authservice.models.dtos.requests.ProjectRequestDTO;
import com.kodeinc.authservice.models.dtos.requests.SearchRequestDTO;
import com.kodeinc.authservice.models.dtos.responses.CustomPage;
import com.kodeinc.authservice.models.dtos.responses.ProjectResponseDTO;
import org.springframework.stereotype.Component;

@Component
public interface ProjectService {


      ProjectResponseDTO create(ProjectRequestDTO request);
      ProjectResponseDTO update(long id,ProjectRequestDTO request);
      CustomPage<ProjectResponseDTO> list(SearchRequestDTO query);
      void delete(long id);


}
