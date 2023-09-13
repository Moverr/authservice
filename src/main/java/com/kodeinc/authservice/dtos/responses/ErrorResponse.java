package com.kodeinc.authservice.dtos.responses;

import lombok.Builder;

/**
 * @author Muyinda Rogers
 * @Date 2023-09-10
 * @Email moverr@gmail.com
 */

@Builder
public class ErrorResponse {
    String code;
    String msg;

}
