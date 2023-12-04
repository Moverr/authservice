package com.kodeinc.authservice.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Muyinda Rogers
 * @Date 2023-09-16
 * @Email moverr@gmail.com
 */

@Entity
@Table(name = "project_resources")
@Getter
@Setter

public class ProjectResource  extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(name = "resource")
    private String name;

    @Column(name = "table_structure", columnDefinition = "json")
    private String tableStructure;


}
