package com.kodeinc.authservice.models.dtos.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author  moverr@gmail.com
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@RequiredArgsConstructor
public class ApiResponse<T> implements Serializable {

    private static final long serialVersionUID = 2606790555796441397L;

    private boolean status;
    private int successCode;
    private String successDescription;
    private int errorCode;
    private String errorDescription;
    private T data;
}
