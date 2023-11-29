package com.kodeinc.authservice.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Set;


@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role  extends BaseEntity  {


    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;


    @Column(name = "issystem")
    private Boolean isSystem;


    @ManyToMany
    @JoinTable(
            name = "role_permission",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions;



}
