package com.accountmanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "master_states")
@Data
public class MasterState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "country_id")
    private Integer countryId;

    @Column(name = "country_code", length = 4)
    private String countryCode;

    @Column(name = "state_code", length = 4)
    private String stateCode;

    @Column(name = "state_name")
    private String stateName;

}
