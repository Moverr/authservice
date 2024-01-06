package com.kodeinc.authservice.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Muyinda Rogers
 * @Date 2024-01-07
 * @Email moverr@gmail.com
 */
@Entity
@Table(name = "user_project")
@Getter
@Setter
public class UserProject extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;

    @ManyToOne
    @JoinColumn(name = "project_id")
    public Project project;

}
