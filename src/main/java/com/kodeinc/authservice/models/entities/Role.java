package com.kodeinc.authservice.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role  extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(name = "code")
    private String code;

}
