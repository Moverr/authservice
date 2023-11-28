package com.kodeinc.authservice.services;


import com.kodeinc.authservice.dtos.requests.UserRequest;
import com.kodeinc.authservice.dtos.responses.UserResponse;
import org.springframework.security.core.userdetails.UserDetails;

public  interface UsersService {
     UserResponse create(UserRequest request);
}