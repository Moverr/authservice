package com.kodeinc.authservice.models.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Muyinda Rogers
 * @Date 2023-12-13
 * @Email moverr@gmail.com
 */

@Getter
@Setter
@Builder
public class ProjectResourceResponse {
    @JsonProperty( "id")
    private long id;

    @JsonProperty( "name")
    private String name;


    @JsonProperty("table_structure")
    private String tableStructure;


}
