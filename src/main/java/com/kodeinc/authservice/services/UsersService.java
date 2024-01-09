package com.kodeinc.authservice.services;


import com.kodeinc.authservice.models.dtos.requests.UserRequest;
import com.kodeinc.authservice.models.dtos.responses.UserResponse;
import jakarta.servlet.http.HttpServletRequest;

public  interface UsersService {
     UserResponse create(HttpServletRequest httpServletRequest, UserRequest request);

     //todo: list users [all, by project, by role]
     UserResponse activate(HttpServletRequest httpServletRequest,long userId);

     UserResponse deactivate(HttpServletRequest httpServletRequest, long userId);


}