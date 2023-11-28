package com.kodeinc.authservice.services.impl;

import com.kodeinc.authservice.models.dtos.requests.RoleRequest;
import com.kodeinc.authservice.models.dtos.requests.SearchRequest;
import com.kodeinc.authservice.models.dtos.responses.CustomPage;
import com.kodeinc.authservice.models.dtos.responses.RoleResponseDTO;
import com.kodeinc.authservice.models.entities.Role;
import com.kodeinc.authservice.repositories.RoleRepository;
import com.kodeinc.authservice.services.BasicService;
import com.kodeinc.authservice.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
 public class RoleServiceImpl implements RoleService,BasicService<RoleRequest,RoleResponseDTO, Role> {

    @Autowired
    private RoleRepository roleRepository;

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


    @Override
    public Set<Role> findRoles(List<Long> roleIds){
        return new HashSet<>(roleRepository.findAllById(roleIds));
    }


}
