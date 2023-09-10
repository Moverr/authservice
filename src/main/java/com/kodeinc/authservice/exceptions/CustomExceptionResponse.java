package com.kodeinc.authservice.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomExceptionResponse {

    private LocalDateTime timestamp;

    private String status;

    private String message;

    private String error;

    private String path;

    @JsonProperty("error_description")
    private String errorDescription;
}
