package com.tvs.iot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Configuration
@Profile({"dev", "default"})
public class DevSystemConfiguration implements SystemConfiguration {





    // private String workstationimagemodule =  "/Users/sivadineshm/documents/backup/img/tvs";

    private String workstationimagemodule =  "/Library/WebServer/Documents/assets/img/tvs";


    private String memberupload = "";


    private String baseurl = "http://localhost";

    private String[] allowDomain = {"http://localhost:8100", "http://192.168.1.7:8100", "*", "http://localhost:8000", "http://192.168.0.103:8100",
            "http://localhost:8080", "http://localhost:80", "http://localhost:4200", "http://127.0.0.1:4200", "http://www.squapl.com", "squapl.com", "www.squapl.com", "http://localhost"};

    public String[] getAllowedDomains() {
        return this.allowDomain;
    }

    public String getBaseURL() {
        return this.baseurl;
    }


    public String getWorkStationImageModuleUploadFolder() {
        return this.workstationimagemodule;
    }




}
