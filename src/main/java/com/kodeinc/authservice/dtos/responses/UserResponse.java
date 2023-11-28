package com.kodeinc.authservice.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Muyinda Rogers
 * @Date 2023-09-10
 * @Email moverr@gmail.com
 */

@Getter
@Setter
public class UserResponse {
    @JsonProperty("username")
    private String username;
    @JsonProperty("roles")
    private List<String> roles;
    @JsonProperty("permissions")
    private List<String> permissions;
}
