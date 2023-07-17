package com.kodeinc.authservice.services;

import com.kodeinc.authservice.models.dtos.requests.ProjectRequestDTO;
import com.kodeinc.authservice.models.dtos.requests.RoleRequestDTO;
import com.kodeinc.authservice.models.dtos.requests.SearchRequestDTO;
import com.kodeinc.authservice.models.dtos.responses.CustomPage;
import com.kodeinc.authservice.models.dtos.responses.ProjectResponseDTO;
import com.kodeinc.authservice.models.dtos.responses.RoleResponseDTO;
import com.kodeinc.authservice.models.entities.Role;

import java.util.Optional;

public interface RolesService  extends BasicService<RoleRequestDTO,RoleResponseDTO, Role> {

    @Override
    RoleResponseDTO create(RoleRequestDTO request);
    RoleResponseDTO update(long id,RoleRequestDTO request);
    CustomPage<RoleResponseDTO> list(SearchRequestDTO query);
    Optional<RoleResponseDTO> getByID(long id);
    void delete(long id);
}
