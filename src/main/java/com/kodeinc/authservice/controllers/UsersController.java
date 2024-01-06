package com.kodeinc.authservice.controllers;

import com.kodeinc.authservice.models.dtos.responses.UserResponse;
import com.kodeinc.authservice.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Muyinda Rogers
 * @Date 2024-01-07
 * @Email moverr@gmail.com
 */
public class UsersController extends BaseController<UserResponse>{
    @Autowired
    UsersService service;


}
