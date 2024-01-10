package com.kodeinc.authservice.services.impl;

import com.kodeinc.authservice.exceptions.KhoodiUnAuthroizedException;
import com.kodeinc.authservice.models.dtos.responses.*;
import com.kodeinc.authservice.services.AuthService;
import com.kodeinc.authservice.services.BaseService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

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

        Optional<PermissionResponse> permissionResponse = user.getRoles()
                .stream()
                .filter(role ->
                     expectedRoles.stream().anyMatch(expectedRole -> expectedRole.getName().equalsIgnoreCase(role.getName()))
                ).flatMap(role ->
                    role.getPermissions().stream()
                )
                .filter(permission ->
                    expectedRoles.stream().flatMap(expectedRole -> expectedRole.getPermissions().stream())
                            .anyMatch(expectedPerm -> expectedPerm.getResource().equalsIgnoreCase(permission.getResource()))
                ).findFirst();

        return permissionResponse.orElseThrow(()-> new KhoodiUnAuthroizedException("You are not authorized to access this resource"));
    }


    public AuthorizeRequestResponse authorizeRequestPermissions(HttpServletRequest request, List<PermissionResponse> expectedPermissions) {

        if(expectedPermissions == null) return null;

        AuthResponse response = validateAuth(request);
        UserResponse user = response.getUser();

        Optional<AuthorizeRequestResponse> authorizeRequestResponse = user.getRoles().stream()
                .flatMap(role ->role.getPermissions().stream())
                .filter(permission -> expectedPermissions.stream()
                        .anyMatch(expectedPerm -> expectedPerm.getResource().equalsIgnoreCase(permission.getResource()))
                )
                .map(permission -> AuthorizeRequestResponse.builder()
                        .auth(response)
                        .permission(permission)
                        .build())
                .findFirst();

        return authorizeRequestResponse.orElseThrow(()-> new KhoodiUnAuthroizedException("You are not authorized to access this resource"));

    }


    protected static <T, R> CustomPage<R> getCustomPage(Page<T> pageData, List<R> listData) {
        CustomPage<R> customResponse = new CustomPage<>();
        customResponse.setData(listData);
        customResponse.setPageNumber(pageData.getNumber());
        customResponse.setPageSize(pageData.getSize());
        customResponse.setPageNumber(pageData.getNumber());
        customResponse.setTotalElements(pageData.getTotalElements());
        return customResponse;
    }


}
