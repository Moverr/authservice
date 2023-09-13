package com.kodeinc.authservice.services.impl;

import com.kodeinc.authservice.configs.CustomAuthenticationManager;
import com.kodeinc.authservice.configs.JwtUtils;
import com.kodeinc.authservice.exceptions.KhoodiUnAuthroizedException;
import com.kodeinc.authservice.models.dtos.requests.LoginRequest;
import com.kodeinc.authservice.models.dtos.responses.AuthResponse;
import com.kodeinc.authservice.models.dtos.responses.AuthTokenResponse;
import com.kodeinc.authservice.models.dtos.responses.RefreshTokenResponse;
import com.kodeinc.authservice.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.kodeinc.authservice.configs.JwtUtils.AUTH_TOKEN_EXPIRATION_MS;
import static com.kodeinc.authservice.configs.JwtUtils.REFRESH_TOKEN_EXPIRATION_MS;

/**
 * @author Muyinda Rogers
 * @Date 2023-09-10
 * @Email moverr@gmail.com
 */

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private CustomAuthenticationManager authenticationManager;

    @Autowired
    private UserDetailServiceImpl userDetailsService;

    @Autowired
    private JwtUtils JwtTokenUtil;


    @Override
    public AuthResponse authenticate(LoginRequest request) {
        validateAuthentication(request.getUsername(), request.getPassword());
        final UserDetails user = userDetailsService.loadUserByUsername(request.getUsername());
        if (user == null)
            throw new KhoodiUnAuthroizedException("User is not  Authorized ");
        return populate(user);
    }

    /*
     *
     * Validate Authentications
     * @Param username
     * @Param password
     */
    public void validateAuthentication(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        if (!authentication.isAuthenticated())
            throw new KhoodiUnAuthroizedException("Un Authorized Access");

    }

    private AuthResponse populate(UserDetails user) {

        String authTokenValue = JwtTokenUtil.generateToken(user);
        // Generate a refresh token
        String refreshTokenValue = JwtTokenUtil.generateRefreshToken(user);

        String authTokenExpiration = new Date(System.currentTimeMillis() + AUTH_TOKEN_EXPIRATION_MS).toString();
        String refreshTokenExpiration = new Date(System.currentTimeMillis()  + REFRESH_TOKEN_EXPIRATION_MS).toString();

        AuthTokenResponse authToken =  AuthTokenResponse.builder().token(authTokenValue).expirationDate(authTokenExpiration).build();
        RefreshTokenResponse refreshToken =  RefreshTokenResponse.builder().refreshToken(refreshTokenValue).expirationDate(refreshTokenExpiration).build();


        AuthResponse authResponse = AuthResponse.builder()
                .username(user.getUsername())
                .authToken(authToken)
                .refreshToken(refreshToken)
                .accountLocked(user.isAccountNonLocked())
                .enabled(user.isEnabled())
                .credentialsNonExpired(user.isCredentialsNonExpired())
                .build();


        return authResponse;
    }

}
