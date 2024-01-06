package com.kodeinc.authservice.controllers;

import com.kodeinc.authservice.models.dtos.requests.UserRequest;
import com.kodeinc.authservice.models.dtos.responses.UserResponse;
import com.kodeinc.authservice.services.UsersService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Muyinda Rogers
 * @Date 2024-01-07
 * @Email moverr@gmail.com
 */
@RestController("/v1/users")
public class UsersController extends BaseController<UserResponse>{
    @Autowired
    UsersService service;

    //todo: create
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
