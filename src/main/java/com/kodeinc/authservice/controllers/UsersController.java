package com.kodeinc.authservice.controllers;

import com.kodeinc.authservice.models.dtos.requests.UserRequest;
import com.kodeinc.authservice.models.dtos.responses.UserResponse;
import com.kodeinc.authservice.services.UsersService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Muyinda Rogers
 * @Date 2024-01-07
 * @Email moverr@gmail.com
 */

@RestController
@RequestMapping("/v1/users")
@Validated

public class UsersController extends BaseController<UserResponse>{
    @Autowired
    UsersService service;


    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> create(
            HttpServletRequest httpServletRequest,
            @RequestBody @Valid UserRequest request){
        return  ResponseEntity.ok(service.create(httpServletRequest,request));
    }



    @PostMapping(value = "activate/{id}",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> activate(
            HttpServletRequest httpServletRequest,
            @PathVariable  long id
    ){
        return  ResponseEntity.ok(service.activate(httpServletRequest,id));
    }


    @PostMapping(value = "deactivate/{id}",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> deactivate(
            HttpServletRequest httpServletRequest,
            @PathVariable  long id
    ){
        return  ResponseEntity.ok(service.deactivate(httpServletRequest,id));
    }

}
