package com.kodeinc.authservice.models.dtos.responses;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Muyinda Rogers
 * @Date 2023-12-03
 * @Email moverr@gmail.com
 */

@Getter
@Setter
@Builder
public class AuthorizeRequestResponse {
    private AuthResponse auth;
    private PermissionResponse permission;
}
