package com.kodeinc.authservice.models.entities;

import jakarta.persistence.*;


@Entity
@Table(name="permissions")
public class Permission extends BaseEntity{



    @ManyToOne
    @JoinColumn(name = "role")
    private Role role;

    @Column(name = "create")
    private String create;

    @Column(name = "read")
    private String read;


    @Column(name = "update")
    private String update;


    @Column(name = "delete")
    private String delete;


    @Column(name = "comment")
    private String comment;

    @Column(name = "resource")
    private String resource;




}
