package com.kodeinc.authservice.controllers;

import com.kodeinc.authservice.models.dtos.requests.LoginRequest;
import com.kodeinc.authservice.models.dtos.responses.AuthResponse;
import com.kodeinc.authservice.services.impl.AuthServiceImpl;
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



    @Autowired
    private  AuthServiceImpl service;



    @GetMapping
    public  ResponseEntity<String> authenticate(){
       return ResponseEntity.ok("TEsted");
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthResponse> authenticate(
            @RequestBody LoginRequest loginRequest
    ) {
        return  ResponseEntity.ok(service.authenticate(loginRequest));
    }
}
