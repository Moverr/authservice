package com.kodeinc.authservice.models.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProjectResponse {
    private   long id;
    @JsonProperty("name")
    private String name;

    @JsonProperty( "code")
    private String code;

    @JsonProperty("callback_url")
    private String callbackUrl;



}
