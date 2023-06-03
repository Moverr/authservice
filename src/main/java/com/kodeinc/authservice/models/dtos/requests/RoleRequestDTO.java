package com.kodeinc.authservice.models.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleRequestDTO {

    @JsonProperty("project")
    private MiniProjectRequestDTO project;
    @JsonProperty("name")
    private String name;
    @JsonProperty("code")
    private String code;
    @JsonProperty("is_system")
    private Boolean isSystem;


    @Getter
    @Setter
    class MiniProjectRequestDTO{
        private Long id;
        private String name;
    }
}
