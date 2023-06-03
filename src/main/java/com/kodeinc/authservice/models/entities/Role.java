package com.kodeinc.authservice.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Table;

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
