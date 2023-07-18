package com.kodeinc.authservice.services.impl;

import com.kodeinc.authservice.models.dtos.requests.RoleRequestDTO;
import com.kodeinc.authservice.models.dtos.requests.SearchRequestDTO;
import com.kodeinc.authservice.models.dtos.responses.CustomPage;
import com.kodeinc.authservice.models.dtos.responses.RoleResponseDTO;
import com.kodeinc.authservice.models.entities.Role;
import com.kodeinc.authservice.services.BasicService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
 class RoleServiceImpl implements BasicService<RoleRequestDTO,RoleResponseDTO, Role> {
    @Override
    public RoleResponseDTO create(RoleRequestDTO request) {
        return null;
    }

    @Override
    public RoleResponseDTO update(long id, RoleRequestDTO request) {
        return null;
    }

    @Override
    public CustomPage<RoleResponseDTO> list(SearchRequestDTO query) {
        return null;
    }

    @Override
    public Optional<RoleResponseDTO> getByID(long id) {
        return Optional.empty();
    }

    @Override
    public void delete(long id) {

    }
}
