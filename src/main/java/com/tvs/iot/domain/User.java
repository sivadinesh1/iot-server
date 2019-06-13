package com.tvs.iot.domain;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;


import javax.persistence.*;

@Entity
@Data
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "userId", nullable = false, updatable = false)
    private Long userId;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private int enabled;
    private String status;

}