package com.kodeinc.authservice.models.dtos.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Muyinda Rogers
 * @Date 2023-07-30
 * @Email moverr@gmail.com
 */
@Getter
@Setter
public class AuthenticationRequest {
    private String username;
    private String password;
}
