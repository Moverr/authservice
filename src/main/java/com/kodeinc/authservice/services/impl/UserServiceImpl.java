package com.kodeinc.authservice.services.impl;

import com.kodeinc.authservice.exceptions.CustomBadRequestException;
import com.kodeinc.authservice.models.dtos.requests.UserRequest;
import com.kodeinc.authservice.models.dtos.responses.UserResponse;
import com.kodeinc.authservice.models.entities.CustomUserDetails;
import com.kodeinc.authservice.models.entities.Role;
import com.kodeinc.authservice.models.entities.User;
import com.kodeinc.authservice.repositories.UserRepository;
import com.kodeinc.authservice.services.RoleService;
import com.kodeinc.authservice.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.kodeinc.authservice.helpers.Utilities.passwordEncoder;

/**
 * @author Muyinda Rogers
 * @Date 2023-07-31
 * @Email moverr@gmail.com
 */

@Service
public class UserServiceImpl implements UsersService, UserDetailsService{

    static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    static String hashedPassword = passwordEncoder.encode("password");



    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;



    // Development..
    public  UserDetails findUserByEmail(String username){
        return  this.loadUserByUsername(username);
    }

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        Optional<User> optionalUser = userRepository.findByUsername(username);

        User user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("User does not exist in the system"));

        return new CustomUserDetails(
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                true, true, true,user.getAuthorities(),user.getRoles()
        );


    }

    @Override
    public UserResponse create(UserRequest request) {
    //todo: va;idate permissions, validate user should not exist. and them create
        Optional<User> optionalUser = userRepository.findByUsername(request.getUsername());
        if(optionalUser.isPresent()){
            //todo: thinking point, user vs multiple projects
            throw new CustomBadRequestException("Username already exists in the database");
        }

        //todo: get Existing Roles..
        Set<Role> roles =  roleService.findRoles(request.getRoles());

        //todo: setup the user with known roles and permissions

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder().encode(request.getPassword()));
        user.setEnabled(true);
        user.setRoles(roles);
        user =   userRepository.save(user);
       return populate(user);
    }





    public UserResponse populate(User entity){
        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(entity.getId());
        userResponse.setUsername(entity.getUsername());
        userResponse.setRoles( entity.getRoles().stream().map(x->roleService.populate(x)).collect(Collectors.toList()));
        return userResponse;
    }

}
