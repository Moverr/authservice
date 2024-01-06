package com.kodeinc.authservice.services;


import com.kodeinc.authservice.models.dtos.requests.UserRequest;
import com.kodeinc.authservice.models.dtos.responses.UserResponse;
import jakarta.servlet.http.HttpServletRequest;

public  interface UsersService {
     UserResponse create(HttpServletRequest httpServletRequest, UserRequest request);

     //todo: update
     UserResponse update(HttpServletRequest httpServletRequest, UserRequest request);

     //todo:
     UserResponse activate(HttpServletRequest httpServletRequest, UserRequest request);

     UserResponse deactivate(HttpServletRequest httpServletRequest, UserRequest request);


}