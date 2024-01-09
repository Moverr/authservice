package com.kodeinc.authservice.models.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kodeinc.authservice.models.entities.ProjectResource;
import com.kodeinc.authservice.models.entities.entityenums.GeneralStatusEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Muyinda Rogers
 * @Date 2023-11-28
 * @Email moverr@gmail.com
 */
@Getter
@Setter
public class UserResponse {
    @JsonProperty("user_id")
    private long userId;

    @JsonProperty("username")
    private String username;

    @JsonProperty("is_active")
    private  boolean isActive;

    @JsonProperty("status")
    private GeneralStatusEnum status;

    @JsonProperty("roles")
    private List<RoleResponse> roles;


    @JsonProperty("projects")
    private List<ProjectResponse> projects;


}
