package com.kodeinc.authservice.models.dtos.responses;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author Muyinda Rogers
 * @Date 2023-09-13
 * @Email moverr@gmail.com
 */
public class UserDetailResponse {
  String username;
  boolean accountLocked;
  boolean credentialsNonExpired;
  boolean enabled;

}
