package com.kodeinc.authservice.services.impl;

import com.kodeinc.authservice.models.dtos.requests.RoleRequest;
import com.kodeinc.authservice.models.dtos.requests.SearchRequest;
import com.kodeinc.authservice.models.dtos.responses.CustomPage;
import com.kodeinc.authservice.models.dtos.responses.RoleResponseDTO;
import com.kodeinc.authservice.models.entities.Role;
import com.kodeinc.authservice.services.BasicService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
 public class RoleServiceImpl implements BasicService<RoleRequest,RoleResponseDTO, Role> {
    @Override
    public RoleResponseDTO create(RoleRequest request) {
        return null;
    }

    @Override
    public RoleResponseDTO update(long id, RoleRequest request) {
        return null;
    }

    @Override
    public CustomPage<RoleResponseDTO> list(SearchRequest query) {
        return null;
    }

    @Override
    public RoleResponseDTO getByID(long id) {
        return  null;
    }

    @Override
    public void delete(long id) {

    }
}
