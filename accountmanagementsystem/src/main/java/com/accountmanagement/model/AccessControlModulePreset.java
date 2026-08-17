package com.accountmanagement.model;

import com.accountmanagement.model.listeners.AccessControlModulePresetListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "access_control_module_presets")
@Data
@EntityListeners(AccessControlModulePresetListener.class)
public class AccessControlModulePreset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "module_name")
    private String moduleName;

    @Column(name = "preset_name")
    private String presetName;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private String status;

}
