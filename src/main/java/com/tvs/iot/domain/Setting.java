package com.tvs.iot.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "setting")
public class Setting {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;
    private String key_settings;
    private String value_settings;


}