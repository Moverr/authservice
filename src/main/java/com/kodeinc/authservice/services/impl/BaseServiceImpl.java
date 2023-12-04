package com.kodeinc.authservice.services.impl;

import com.kodeinc.authservice.models.dtos.responses.*;
import com.kodeinc.authservice.services.AuthService;
import com.kodeinc.authservice.services.BaseService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Muyinda Rogers
 * @Date 2023-12-02
 * @Email moverr@gmail.com
 */
@Component
public class BaseServiceImpl implements BaseService {

    @Autowired
    private AuthService service;

    public AuthResponse validateAuth(HttpServletRequest request) {
        return service.authenticate(request);
    }

    public PermissionResponse authorizeRequestRoles(HttpServletRequest request, List<RoleResponse> expectedRoles) {

        if(expectedRoles == null) return null;

        AuthResponse response = validateAuth(request);
        UserResponse user = response.getUser();

        for (RoleResponse roleResponse : user.getRoles()) {
            for (RoleResponse expectedRole : expectedRoles) {
                //todo: find if there is a role as expected , exists in the user response
                if (expectedRole.getName().equalsIgnoreCase(roleResponse.getName())) {
                    for (PermissionResponse permissionResponse : roleResponse.getPermissions()) {
                        for (PermissionResponse expectedPermision : expectedRole.getPermissions()) {
                            //todo: find if there is a user permission pre set to the role.
                            if (expectedPermision.getResource().equalsIgnoreCase(permissionResponse.getResource())) {
                                 return permissionResponse;
                            }
                        }
                    }
                }
            }
        }


        return null;
    }


    public AuthorizeRequestResponse authorizeRequestPermissions(HttpServletRequest request, List<PermissionResponse> expectedPermissions) {

        if(expectedPermissions == null) return null;

        AuthResponse response = validateAuth(request);
        UserResponse user = response.getUser();

        for (RoleResponse roleResponse : user.getRoles()) {


                    for (PermissionResponse permissionResponse : roleResponse.getPermissions()) {
                        for (PermissionResponse expectedPermision : expectedPermissions) {
                            //todo: find if there is a user permission pre set to the role.
                            if (expectedPermision.getResource().equalsIgnoreCase(permissionResponse.getResource())) {
                                /*
                                Return user permission. further checking would be
                                on the level of permission ..
                                 */
                                return  AuthorizeRequestResponse.builder()
                                        .auth(response)
                                        .permission(permissionResponse).build()
                                ;

                            }
                        }
                    }


        }


        return null;
    }



}
