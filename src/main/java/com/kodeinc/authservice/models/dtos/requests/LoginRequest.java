package com.kodeinc.authservice.models.dtos.requests;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Muyinda Rogers
 * @Date 2023-07-30
 * @Email moverr@gmail.com
 */
@Getter
@Setter
public class LoginRequest {
    private String username;
    private String password;
}
