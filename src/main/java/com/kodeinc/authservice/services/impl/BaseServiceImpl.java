package com.kodeinc.authservice.services.impl;

import com.kodeinc.authservice.models.dtos.responses.AuthResponse;
import com.kodeinc.authservice.models.dtos.responses.PermissionResponse;
import com.kodeinc.authservice.models.dtos.responses.RoleResponse;
import com.kodeinc.authservice.models.dtos.responses.UserResponse;
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

    public PermissionResponse authorizeRequest(HttpServletRequest request, List<RoleResponse> expectedRoles) {

        AuthResponse response = validateAuth(request);
        UserResponse user = response.getUser();

        for (RoleResponse roleResponse : user.getRoles()) {
            for (RoleResponse expectedRole : expectedRoles) {
                //todo: find if there is a role as expected , exists in the user response
                if (expectedRole.getName().equalsIgnoreCase(roleResponse.getName())) {
                    for (PermissionResponse permissionResponse : roleResponse.getPermissions()) {
                        for (PermissionResponse expectedPermision : expectedRole.getPermissions()) {
                            //todo: find if there is a user permission pre set to the role.
                            if (expectedPermision.getName().equalsIgnoreCase(permissionResponse.getName())) {
                                 return permissionResponse;
                            }
                        }
                    }
                }
            }
        }


        return null;
    }


}
