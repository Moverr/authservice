package com.kodeinc.authservice.models.dtos.responses;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Muyinda Rogers
 * @Date 2023-09-13
 * @Email moverr@gmail.com
 */

@Getter
@Setter
public class AuthResponse {
    private String username;

    private AuthToken authToken;
    private  RefreshToken refreshToken;
    private boolean accountLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    //todo: profile information missing :: probaly not important at the moment ..


}
