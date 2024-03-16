package com.kodeinc.authservice.services;


import com.kodeinc.authservice.models.dtos.requests.UserRequest;
import com.kodeinc.authservice.models.dtos.requests.UsersSearchQuery;
import com.kodeinc.authservice.models.dtos.responses.CustomPage;
import com.kodeinc.authservice.models.dtos.responses.UserResponse;
import com.kodeinc.authservice.models.entities.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;

public  interface UsersService {

     CustomUserDetails loadUserByUsername(String username);
      UserDetails findUserByEmail(String username);
     UserResponse create(HttpServletRequest httpServletRequest, UserRequest request);


     UserResponse activate(HttpServletRequest httpServletRequest,long userId);

     UserResponse deactivate(HttpServletRequest httpServletRequest, long userId);

     CustomPage<UserResponse> list(HttpServletRequest httpServletRequest, UsersSearchQuery query);



}