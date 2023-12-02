package com.kodeinc.authservice.controllers;

import com.kodeinc.authservice.configs.security.JwtUtils;
import com.kodeinc.authservice.exceptions.KhoodiUnAuthroizedException;
import com.kodeinc.authservice.models.dtos.requests.LoginRequest;
import com.kodeinc.authservice.models.dtos.responses.AuthResponse;
import com.kodeinc.authservice.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Muyinda Rogers
 * @Date 2023-07-30
 * @Email moverr@gmail.com
 */
@RestController
@RequestMapping("api/v1/auth")

public class AuthenticationController extends BaseController<AuthResponse>{

    /*
    *
    * The Authentication controller, manages the login, authentications,
    *  Access Tokens, Reset Tokens
     */


    @Autowired
    private AuthService service;

    //todo: request refresh token

    /*
    Validate request token and respond with full details of the principal
     */

    @PostMapping("/validate")
    public  ResponseEntity<AuthResponse>  validateToken(final HttpServletRequest request){
        String token = JwtUtils.extractToken(request);

        if (token != null && JwtUtils.validateToken(token))
            return  ResponseEntity.ok(service.authenticate(token));
         else
             throw new KhoodiUnAuthroizedException("Invalid or missing token");


    }
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthResponse> authenticate(
            @RequestBody LoginRequest loginRequest
    ) {
        return  ResponseEntity.ok(service.authenticate(loginRequest));
    }
}
