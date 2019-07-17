package com.tvs.iot.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "settings")
public class Setting {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;
    private String key_settings;
    private String value_settings;

    private Long createdby;
    private Date createddatetime;

    private Long updatedby;
    private Date updateddatetime;
}