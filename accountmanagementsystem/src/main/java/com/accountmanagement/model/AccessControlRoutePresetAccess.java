package com.accountmanagement.model;

import java.time.LocalDateTime;

import com.accountmanagement.model.listeners.AccessControlRoutePresetAccessListener;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "access_control_route_preset_access")
@Data
@EntityListeners(AccessControlRoutePresetAccessListener.class)
public class AccessControlRoutePresetAccess {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "module_preset_id")
    private Integer modulePresetId;

    @Column(name = "route_id")
    private Integer routeId;

    @Column(name = "status")
    private String status;

    @JsonIgnore
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonIgnore
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
