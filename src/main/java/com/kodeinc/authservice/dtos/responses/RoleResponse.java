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
public class RoleResponse {
    @JsonProperty("name")
    private String role;
    @JsonProperty("permissions")
    private List<String> permissions;
}
