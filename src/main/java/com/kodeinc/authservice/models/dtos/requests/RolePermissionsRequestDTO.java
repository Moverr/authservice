package com.kodeinc.authservice.models.dtos.requests;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class RolePermissionsRequestDTO {

    private long role_id;
    private List<PermissionRequestDTO> permissions;


}
