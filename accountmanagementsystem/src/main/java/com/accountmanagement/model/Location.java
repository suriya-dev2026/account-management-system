package com.accountmanagement.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.UuidGenerator;

import com.accountmanagement.model.listeners.AreaListeners;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "locations")
@EntityListeners(AreaListeners.class)
@Data
public class Location {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private String id;

    @Column(name = "area")
    private String area;

    @Column(name = "description")
    private String description;

    @JsonIgnore
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonIgnore
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
