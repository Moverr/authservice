package com.kodeinc.authservice.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Muyinda Rogers
 * @Date 2023-09-17
 * @Email moverr@gmail.com
 */
@Entity
@Table(name = "roles")
@Getter
@Setter
public class User extends BaseEntity{
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;


    @Column(name = "firstname")
    private String firstname;


    @Column(name = "lastname")
    private String lastname;

    @Column(name = "enabled")
    private boolean enabled;

}