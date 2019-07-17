package com.tvs.iot.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigInteger;


@Data
public class UserDTO {
//    public BigInteger getUserid() {
//        return userid;
//    }
//
//    public void setUserid(BigInteger userid) {
//        this.userid = userid;
//    }

    private java.math.BigInteger userid;
    private String firstname;
    private String lastname;
    private String role;
}
