package com.kodeinc.authservice.models.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
@Table(name = "projects")
@Getter
@Setter
public class Project extends BaseEntity{


    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "callback_url")
    private String callbackUrl;

    @Transient
    @OneToMany(mappedBy = "project")
    private List<ProjectResource> resources;

}