package com.tvs.iot.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "model")
public class Model {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    private String partnumber;
    private String module;
    private Integer nooftags;
    private String isactive;

    private Long createdby;
    private Date createddatetime;

    private Long updatedby;
    private Date updateddatetime;
}


