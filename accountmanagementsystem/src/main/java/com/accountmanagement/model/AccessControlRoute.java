package com.accountmanagement.model;

import com.accountmanagement.model.listeners.AccessControlRouteListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "access_control_routes")
@Data
@EntityListeners(AccessControlRouteListener.class)
public class AccessControlRoute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "controller_name")
    private String controllerName;

    @Column(name = "backend_route")
    private String backendRoute;

    @Column(name = "frontend_route")
    private String frontendRoute;

    @Column(name = "description")
    private String description;

    @Column(name = "is_default")
    private Integer isDefault;

    @Column(name = "status")
    private String status;

}
