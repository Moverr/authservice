package com.kodeinc.authservice.models.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.kodeinc.authservice.models.entities.entityenums.GeneralStatusEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@MappedSuperclass
public class BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @jakarta.persistence.Column(name = "id", nullable = false)
    private Long id;


    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    @ColumnDefault("now()")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    @ColumnDefault("now()")

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime updatedAt;

    @JsonProperty("updated_by")
    @Column(name = "updated_by")
    private Long updatedBy;

    @JsonProperty("created_by")
    @Column(name = "created_by")
    private Long createdBy;

    @JsonProperty("status")
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private GeneralStatusEnum status;

}
