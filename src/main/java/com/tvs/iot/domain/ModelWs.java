package com.tvs.iot.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "modelws")
public class ModelWs {




    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;
    private String modelid;
    private String assemblyimagename;
    private String workstation;
    private String assemblyimageurl;

    private Long createdby;
    private Date createddatetime;

    private Long updatedby;
    private Date updateddatetime;

  //  private String tagtype;


}
