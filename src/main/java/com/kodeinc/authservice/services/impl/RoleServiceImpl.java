package com.kodeinc.authservice.services.impl;

import com.kodeinc.authservice.models.dtos.requests.RoleRequest;
import com.kodeinc.authservice.models.dtos.requests.SearchRequest;
import com.kodeinc.authservice.models.dtos.responses.CustomPage;
import com.kodeinc.authservice.models.dtos.responses.PermissionResponse;
import com.kodeinc.authservice.models.dtos.responses.RoleResponse;
import com.kodeinc.authservice.models.dtos.responses.RoleResponseDTO;
import com.kodeinc.authservice.models.entities.Permission;
import com.kodeinc.authservice.models.entities.Role;
import com.kodeinc.authservice.repositories.RoleRepository;
import com.kodeinc.authservice.services.RoleService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
 public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public RoleResponseDTO create(HttpServletRequest httpServletRequest, RoleRequest request) {
        return null;
    }

    @Override
    public RoleResponseDTO update(HttpServletRequest httpServletRequest, long id, RoleRequest request) {
        return null;
    }

    @Override
    public CustomPage<RoleResponseDTO> list(HttpServletRequest httpServletRequest, SearchRequest query) {
        return null;
    }

    @Override
    public RoleResponseDTO getByID(HttpServletRequest httpServletRequest, long id) {
        return  null;
    }

    @Override
    public void delete(HttpServletRequest httpServletRequest, long id) {

    }



    @Override
    public Set<Role> findRoles(List<Long> roleIds){
        log.info("Find Roles Method");
       return  roleIds == null ? null :
          new HashSet<>(roleRepository.findAllById(roleIds));
    }



    @Override
    public RoleResponse populate(Role entity){
        RoleResponse roleResponse = new RoleResponse();
        roleResponse.setName(entity.getName());
        roleResponse.setPermissions(entity.getPermissions().stream().map(this::populate).collect(Collectors.toList()));
        return  roleResponse;
    }

    @Override
    public PermissionResponse populate(Permission entity){

        PermissionResponse permissionResponse = new PermissionResponse();
        if(entity.getResource() != null)
            permissionResponse.setResource(entity.getResource().getName());
        permissionResponse.setRead(entity.getRead());
        permissionResponse.setCreate(entity.getCreate());
        permissionResponse.setUpdate(entity.getUpdate());
        permissionResponse.setDelete(entity.getDelete());
        if(entity.getResource() != null)
            permissionResponse.setResource(entity.getResource().getName());


        return  permissionResponse;
    }
}
