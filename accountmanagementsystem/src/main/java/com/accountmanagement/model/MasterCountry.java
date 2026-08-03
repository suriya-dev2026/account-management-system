package com.accountmanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "master_countries")
@Data
public class MasterCountry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "country_code", length = 2)
    private String countryCode;

    @Column(name = "iso3", length = 3)
    private String iso3;

    @Column(name = "country_name", nullable = false)
    private String countryName;

    @Column(name = "num_code")
    private String numCode;

    @Column(name = "sort_order")
    private Integer sortOrder;

}
