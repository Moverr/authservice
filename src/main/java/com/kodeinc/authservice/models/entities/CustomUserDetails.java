package com.kodeinc.authservice.models.entities;

import com.kodeinc.authservice.models.entities.entityenums.GeneralStatusEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Set;

/**
 * @author Muyinda Rogers
 * @Date 2023-11-29
 * @Email moverr@gmail.com
 */
public class CustomUserDetails extends User {

    private Set<Role> customRoles;
    private Set<Permission> customPermissions;

    private  long userId;
    private GeneralStatusEnum status;

    public CustomUserDetails(long userId, String username, String password, boolean enabled, boolean accountNonExpired,
                             boolean credentialsNonExpired, boolean accountNonLocked,
                             Collection<? extends GrantedAuthority> authorities
                             , Set<Role> roles
                             , GeneralStatusEnum status
                             ) {
    super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.customRoles = roles;
        this.userId = userId;
        this.status = status;

    }


    public Set<Role> getCustomRoles() {
        return customRoles;
    }

    public void setCustomRoles(Set<Role> customRoles) {
        this.customRoles = customRoles;
    }

    public Set<Permission> getCustomPermissions() {
        return customPermissions;
    }

    public void setCustomPermissions(Set<Permission> customPermissions) {
        this.customPermissions = customPermissions;
    }

    public long getUserId() {
        return userId;
    }

    public GeneralStatusEnum getStatus() {
        return status;
    }
}
