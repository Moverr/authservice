package com.kodeinc.authservice.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role  extends BaseEntity{


    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;


    @Column(name = "issystem")
    private Boolean isSystem;


}
