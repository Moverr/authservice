package com.kodeinc.authservice.models.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

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




}
