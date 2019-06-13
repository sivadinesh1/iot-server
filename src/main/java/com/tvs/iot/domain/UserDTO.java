package com.tvs.iot.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;


@Data
public class UserDTO {
    private java.math.BigInteger userid;
    private String firstname;
    private String lastname;
    private String role;
}
