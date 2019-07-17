package com.tvs.iot.domain;

import lombok.Data;

import javax.persistence.*;


@Data
public class ModelWsDTO {
    private Long modelid;
    private Long id;
    private String partnumber;
    private String module;
    private String isactive;
    private String workstation;
    private String nooftags;

    private String assemblyimageurl;
    private String assemblyimagename;

    private String tagtype;

}
