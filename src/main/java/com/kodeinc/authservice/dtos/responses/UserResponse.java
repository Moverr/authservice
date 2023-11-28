package com.kodeinc.authservice.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("username")
    private String username;

    //todo: implement role and permissions .
    @JsonProperty("roles")
    private List<RoleResponse> roles;



}
