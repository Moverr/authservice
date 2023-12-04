package com.kodeinc.authservice.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Muyinda Rogers
 * @Date 2023-09-17
 * @Email moverr@gmail.com
 */
@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends BaseEntity implements UserDetails {
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;


    @Column(name = "enabled")
    private boolean enabled;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(

            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Set<GrantedAuthority> authorities = new HashSet<>(roles.stream().map(x -> new SimpleGrantedAuthority(x.getName())).toList());
        // Convert roles to GrantedAuthority
      //  roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        roles.forEach(role->role.getPermissions().forEach(permission -> {
            if(permission.getResource() != null )
                authorities.add(new SimpleGrantedAuthority(permission.getResource().getName()));

        }));


        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }


}
