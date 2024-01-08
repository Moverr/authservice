package com.kodeinc.authservice.services.impl;

import com.kodeinc.authservice.configs.security.JwtUtils;
import com.kodeinc.authservice.exceptions.CustomBadRequestException;
import com.kodeinc.authservice.exceptions.CustomNotFoundException;
import com.kodeinc.authservice.exceptions.KhoodiUnAuthroizedException;
import com.kodeinc.authservice.helpers.Constants;
import com.kodeinc.authservice.models.dtos.requests.UserRequest;
import com.kodeinc.authservice.models.dtos.responses.AuthResponse;
import com.kodeinc.authservice.models.dtos.responses.AuthorizeRequestResponse;
import com.kodeinc.authservice.models.dtos.responses.PermissionResponse;
import com.kodeinc.authservice.models.dtos.responses.UserResponse;
import com.kodeinc.authservice.models.entities.CustomUserDetails;
import com.kodeinc.authservice.models.entities.Project;
import com.kodeinc.authservice.models.entities.Role;
import com.kodeinc.authservice.models.entities.User;
import com.kodeinc.authservice.models.entities.entityenums.GeneralStatusEnum;
import com.kodeinc.authservice.models.entities.entityenums.PermissionLevelEnum;
import com.kodeinc.authservice.repositories.UserRepository;
import com.kodeinc.authservice.services.ProjectService;
import com.kodeinc.authservice.services.RoleService;
import com.kodeinc.authservice.services.UsersService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.kodeinc.authservice.helpers.Utilities.passwordEncoder;

/**
 * @author Muyinda Rogers
 * @Date 2023-07-31
 * @Email moverr@gmail.com
 */

@Slf4j
@Service
public class UserServiceImpl  implements UsersService, UserDetailsService {

    static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    static String hashedPassword = passwordEncoder.encode("password");


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ProjectService projectService;




    // Development..
    public UserDetails findUserByEmail(String username) {
        return this.loadUserByUsername(username);
    }

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        Optional<User> optionalUser = userRepository.findByUsername(username);

        User user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("User does not exist in the system"));

        return new CustomUserDetails(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                true, true, true, user.getAuthorities(), user.getRoles()
        );


    }

    private  AuthorizeRequestResponse authenticate(HttpServletRequest request, List<PermissionResponse> expectedPermissions) throws KhoodiUnAuthroizedException {

        String token = JwtUtils.extractToken(request);

        if (token != null && JwtUtils.validateToken(token)) {
            final String userName = JwtUtils.extractUsername(token.trim());
            final CustomUserDetails userDetails = loadUserByUsername(userName);
            if (userDetails == null) {
                throw new KhoodiUnAuthroizedException("Invalid username or password");
            }
            AuthResponse response =  populateAuthResponse(userDetails);

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



        } else
            throw new KhoodiUnAuthroizedException("Invalid or missing token");

    }

    @Override
    public UserResponse create(HttpServletRequest httpServletRequest, UserRequest request) {

        log.info("UserServiceImpl   create method");
        //todo: validate user access
        AuthorizeRequestResponse authenticatedPermission = authenticate(httpServletRequest, getPermission());
        if (authenticatedPermission.getPermission() != null && (authenticatedPermission.getPermission().getResource().equalsIgnoreCase("ALL_FUNCTIONS") || authenticatedPermission.getPermission().getCreate().equals(PermissionLevelEnum.FULL))) {
            {
                Optional<User> optionalUser = userRepository.findByUsername(request.getUsername());
                if (optionalUser.isPresent()) {
                    throw new CustomBadRequestException("Username already exists in the database");
                }

                Set<Role> roles = roleService.findRoles(request.getRoles());
                Set<Project> projects = new ProjectServiceImpl().findProjects(request.getRoles());


                User user = new User();
                user.setUsername(request.getUsername());
                user.setPassword(passwordEncoder().encode(request.getPassword()));
                user.setEnabled(true);
                user.setStatus(GeneralStatusEnum.ACTIVE);
                user.setRoles(roles);
                user.setProjects(projects);
                user = userRepository.save(user);
                return populate(user);

            }

        } else {
            throw new KhoodiUnAuthroizedException("You dont have permission to create users");
        }

    }



    /**
     * @param httpServletRequest
     * @return
     */
    @Override
    public UserResponse activate(HttpServletRequest httpServletRequest, long userId) {
        //todo: find if user exists
        log.info("UserServiceImpl   create method");
        AuthorizeRequestResponse authenticatedPermission = authenticate(httpServletRequest, getPermission());
        if (authenticatedPermission.getPermission() != null && (authenticatedPermission.getPermission().getResource().equalsIgnoreCase("ALL_FUNCTIONS") || authenticatedPermission.getPermission().getCreate().equals(PermissionLevelEnum.FULL))) {

            User user = userRepository.findById(userId).orElseThrow(() -> new CustomNotFoundException("Project Resource not found"));
            user.setStatus(GeneralStatusEnum.ACTIVE);
            userRepository.save(user);

            return populate(user);
        } else
            throw new KhoodiUnAuthroizedException("You dont have permission to manage users");


    }

    /**
     * @param httpServletRequest
     * @return
     */
    @Override
    public UserResponse deactivate(HttpServletRequest httpServletRequest, long userId) {
        //todo: find if user exists
        log.info("UserServiceImpl   create method");
        AuthorizeRequestResponse authenticatedPermission = authenticate(httpServletRequest, getPermission());
        if (authenticatedPermission.getPermission() != null && (authenticatedPermission.getPermission().getResource().equalsIgnoreCase("ALL_FUNCTIONS") || authenticatedPermission.getPermission().getCreate().equals(PermissionLevelEnum.FULL))) {

            User user = userRepository.findById(userId).orElseThrow(() -> new CustomNotFoundException("Project Resource not found"));
            user.setStatus(GeneralStatusEnum.DEACTIVE);
            userRepository.save(user);

            return populate(user);
        } else
            throw new KhoodiUnAuthroizedException("You dont have permission to manage users");
    }

    private static List<PermissionResponse> getPermission() {
        List<PermissionResponse> expectedPermissions = new ArrayList<>();
        PermissionResponse permissionResponse = new PermissionResponse();
        permissionResponse.setResource("ALL_FUNCTIONS");
        expectedPermissions.add(permissionResponse);
        permissionResponse = new PermissionResponse();
        permissionResponse.setResource("USERS");
        expectedPermissions.add(permissionResponse);
        return expectedPermissions;
    }


    public UserResponse populate(User entity) {
        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(entity.getId());
        userResponse.setUsername(entity.getUsername());

        if(entity.getRoles() != null)
            userResponse.setRoles(entity.getRoles().stream().map(x -> roleService.populate(x)).collect(Collectors.toList()));

        if(entity.getProjects() != null)
            userResponse.setProjects(entity.getProjects().stream().map(x -> projectService.populate(x)).collect(Collectors.toList()));

        return userResponse;
    }


    private AuthResponse populateAuthResponse(CustomUserDetails user) {

        String token = JwtUtils.generateToken(user);
        String refreshToken = JwtUtils.refreshJwtToken(token);


        UserResponse userResponse = new UserResponse();
        userResponse.setUsername(user.getUsername());

        userResponse.setRoles(
                user.getCustomRoles().stream().map(roleService::populate).collect(Collectors.toList())

        );


        AuthResponse response = new AuthResponse();
        response.setMessage(Constants.SUCCESSFUL_LOGIN_MSG);
        response.setSuccess(user.isEnabled());
        response.setAuthToken(token);
        response.setRefreshToken(refreshToken);
        response.setUser(userResponse);

        return response;
    }




}
