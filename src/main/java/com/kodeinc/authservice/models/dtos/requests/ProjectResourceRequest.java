package com.kodeinc.authservice.models.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Muyinda Rogers
 * @Date 2023-12-13
 * @Email moverr@gmail.com
 */
@Getter
@Setter
public class ProjectResourceRequest {

    @NotNull(message = "Project Id is mandatory")
    @JsonProperty("project_id")
    private long projectId;
    @NotNull(message = "Project Id is mandatory")
    @JsonProperty("resource")
    private String name;




}
