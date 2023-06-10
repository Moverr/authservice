package com.kodeinc.authservice.models.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kodeinc.authservice.models.entities.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Transient;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ProjectResponseDTO {
    private   long id;
    @JsonProperty("name")
    private String name;

    @JsonProperty( "code")
    private String code;

    @JsonProperty("callback_url")
    private String callbackUrl;

//    @JsonProperty("roles")
//    private List<RoleResponseDTO> roles;

}
