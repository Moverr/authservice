package com.kodeinc.authservice.models.entities;

import com.kodeinc.authservice.models.entities.entityenums.PermissionLevelEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;


@Getter
@Setter
@Entity
@Table(name="permissions")
public class Permission extends BaseEntity  {


    @Column(name = "create")
    @Enumerated(EnumType.STRING)
    private PermissionLevelEnum create;

    @Column(name = "read")
    @Enumerated(EnumType.STRING)
    private PermissionLevelEnum read; //full/mine//none


    @Column(name = "update")
    @Enumerated(EnumType.STRING)
    private PermissionLevelEnum update;


    @Column(name = "delete")
    @Enumerated(EnumType.STRING)
    private PermissionLevelEnum delete;


    @Column(name = "comment")
    @Enumerated(EnumType.STRING)
    private PermissionLevelEnum comment;



    @Column(name = "readable_fields",columnDefinition = "json")
    private String readableFields;


    @Column(name = "writable_fields",columnDefinition = "json")
    private String writableFields;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "resource_id")
    private ProjectResource resource;

}
