package com.kodeinc.authservice.controllers;

import com.kodeinc.authservice.models.dtos.requests.LoginRequest;
import com.kodeinc.authservice.models.dtos.responses.AuthResponse;
import com.kodeinc.authservice.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Muyinda Rogers
 * @Date 2023-07-30
 * @Email moverr@gmail.com
 */
@RestController
@RequestMapping("v1/auth")

public class AuthenticationController extends BaseController<AuthResponse>{

    /*
    *
    * The Authentication controller, manages the login, authentications,
    *  Access Tokens, Reset Tokens
     */


    @Autowired
    private AuthService service;


    /*
    *
      Validate request token and respond with full details of the principal
     */

    @PostMapping("/validate")
    public  ResponseEntity<AuthResponse>  validateToken(final HttpServletRequest request){
            return  ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(final HttpServletRequest request){
        throw  new RuntimeException("Not yet implemented");
    }


    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthResponse> authenticate(
            @RequestBody LoginRequest loginRequest
    ) {
        return  ResponseEntity.ok(service.authenticate(loginRequest));
    }


}
