package com.kodeinc.authservice.models.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kodeinc.authservice.models.entities.entityenums.PermissionLevelEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Muyinda Rogers
 * @Date 2023-11-29
 * @Email moverr@gmail.com
 */

@Getter
@Setter
public class PermissionResponse {

    @JsonProperty("name")
    private String name;

    @JsonProperty( "create")
    private PermissionLevelEnum create;

    @JsonProperty("read")
    @Enumerated(EnumType.STRING)
    private PermissionLevelEnum read; //full/mine//none


    @JsonProperty("update")
    @Enumerated(EnumType.STRING)
    private PermissionLevelEnum update;


    @JsonProperty("delete")
    @Enumerated(EnumType.STRING)
    private PermissionLevelEnum delete;


    @JsonProperty( "comment")
    @Enumerated(EnumType.STRING)
    private PermissionLevelEnum comment;

    @JsonProperty("resource")
    private String resource;


}
