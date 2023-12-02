package com.kodeinc.authservice.services;


import com.kodeinc.authservice.models.dtos.requests.UserRequest;
import com.kodeinc.authservice.models.dtos.responses.UserResponse;

public  interface UsersService {
     UserResponse create(UserRequest request);
}