package com.kodeinc.authservice.models.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectRequestDTO {
    @JsonProperty("name")
    @NotEmpty(message = "name is mandatory")
    private String name;
    @JsonProperty("code")
    @NotEmpty(message = "code is mandatory")
    private String code;
    @JsonProperty("callback_url")
    private String callbackUrl;
}
