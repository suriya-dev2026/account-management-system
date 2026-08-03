package com.accountmanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "master_cities")
@Data
public class MasterCity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "country_code", length = 4)
    private String countryCode;

    @Column(name = "state_id")
    private Integer stateId;

    @Column(name = "city", nullable = false)
    private String city;
}
