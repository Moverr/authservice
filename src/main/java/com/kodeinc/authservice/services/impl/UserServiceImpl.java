package com.kodeinc.authservice.services.impl;

import com.kodeinc.authservice.configs.security.JwtUtils;
import com.kodeinc.authservice.exceptions.CustomBadRequestException;
import com.kodeinc.authservice.exceptions.CustomNotFoundException;
import com.kodeinc.authservice.exceptions.KhoodiUnAuthroizedException;
import com.kodeinc.authservice.helpers.Constants;
import com.kodeinc.authservice.models.dtos.requests.UserRequest;
import com.kodeinc.authservice.models.dtos.requests.UsersSearchQuery;
import com.kodeinc.authservice.models.dtos.responses.*;
import com.kodeinc.authservice.models.entities.*;
import com.kodeinc.authservice.models.entities.entityenums.GeneralStatusEnum;
import com.kodeinc.authservice.models.entities.entityenums.PermissionLevelEnum;
import com.kodeinc.authservice.repositories.ProjectRepository;
import com.kodeinc.authservice.repositories.RoleRepository;
import com.kodeinc.authservice.repositories.UserRepository;
import com.kodeinc.authservice.services.UsersService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.kodeinc.authservice.helpers.Utilities.passwordEncoder;
import static com.kodeinc.authservice.services.impl.BaseServiceImpl.getCustomPage;

/**
 * @author Muyinda Rogers
 * @Date 2023-07-31
 * @Email moverr@gmail.com
 */

@Slf4j
@Service
public class UserServiceImpl  implements UsersService, UserDetailsService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;




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

                Set<Role> roles = request.getRoles() == null ? null : new HashSet<>(roleRepository.findAllById(request.getRoles()));
                Set<Project> projects =  request.getProjects() == null ? null : new HashSet<>(projectRepository.findAllById(request.getProjects()));


               final  User userEntity = new User();
                userEntity.setUsername(request.getUsername());
                userEntity.setPassword(passwordEncoder().encode(request.getPassword()));
                userEntity.setEnabled(true);
                userEntity.setStatus(GeneralStatusEnum.ACTIVE);
                userEntity.setRoles(roles);
                userEntity.setProjects(projects);

                final User createdUser = userRepository.save(userEntity);
                return populate(createdUser);

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

    /**
     * @param httpServletRequest
     * @param queryRequest
     * @return
     */
    @Override
    public CustomPage<UserResponse> list(HttpServletRequest httpServletRequest, UsersSearchQuery queryRequest) {

        log.info("UserServiceImpl   create method");
        AuthorizeRequestResponse authenticatedPermission = authenticate(httpServletRequest, getPermission());
        if (authenticatedPermission.getPermission() != null && (authenticatedPermission.getPermission().getResource().equalsIgnoreCase("ALL_FUNCTIONS") || authenticatedPermission.getPermission().getResource().equalsIgnoreCase("USERS")) ){

            Sort sort = switch (queryRequest.getSortBy()) {
                case "username" -> Sort.by("username");
                case "id" -> Sort.by("id");
                case "created_at" -> Sort.by("created_at");
                case "updated_at" -> Sort.by("updated_at");
                default -> Sort.by("id");
            };

            sort = switch (queryRequest.getSortType()) {
                case "asc" -> sort.ascending();
                default -> sort.descending();
            };

            Pageable pageable = PageRequest.of(queryRequest.getOffset(), queryRequest.getLimit(), sort);


            final   List<User> users;
            switch (queryRequest.getLevel()){
                case ROLE ->
                     users =  userRepository.findUsersRole(queryRequest.getLevelId(),queryRequest.getStatus().name(),pageable);
                case PROJECT ->
                        users =  userRepository.findUsersProject(queryRequest.getLevelId(),queryRequest.getStatus().name(),pageable);
                default ->
                        users =  userRepository.findUsers(queryRequest.getStatus().name(),pageable);
            }
            long totalRecords = userRepository.count();

            List<UserResponse> responses = users.stream().map(this::populate).collect(Collectors.toList());
            Page<UserResponse> pageResult = new PageImpl<>(responses, pageable, totalRecords);

            return getCustomPage(pageResult, responses);
        }
        else
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
        userResponse.setActive(entity.getStatus().equals(GeneralStatusEnum.ACTIVE));
        userResponse.setStatus(entity.getStatus());

        if(entity.getRoles() != null)
            userResponse.setRoles(entity.getRoles().stream().map(this::populate).collect(Collectors.toList()));

        if(entity.getProjects() != null)
            userResponse.setProjects(entity.getProjects().stream().map(ProjectServiceImpl::populate).collect(Collectors.toList()));

        return userResponse;
    }

    public RoleResponse populate(Role entity){
        RoleResponse roleResponse = new RoleResponse();
        roleResponse.setName(entity.getName());
        roleResponse.setPermissions(entity.getPermissions().stream().map(this::populate).collect(Collectors.toList()));

        return  roleResponse;
    }



    public PermissionResponse populate(Permission entity){

        PermissionResponse permissionResponse = new PermissionResponse();
        if(entity.getResource() != null)
            permissionResponse.setResource(entity.getResource().getName());
        permissionResponse.setRead(entity.getRead());
        permissionResponse.setCreate(entity.getCreate());
        permissionResponse.setUpdate(entity.getUpdate());
        permissionResponse.setDelete(entity.getDelete());
        if(entity.getResource() != null)
            permissionResponse.setResource(entity.getResource().getName());


        return  permissionResponse;
    }
    private AuthResponse populateAuthResponse(CustomUserDetails user) {

        String token = JwtUtils.generateToken(user);
        String refreshToken = JwtUtils.refreshJwtToken(token);


        UserResponse userResponse = new UserResponse();
        userResponse.setUsername(user.getUsername());

        userResponse.setRoles(
                user.getCustomRoles().stream().map(this::populate).collect(Collectors.toList())

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
