

package com.tvs.iot.config;

        import org.springframework.context.annotation.Configuration;
        import org.springframework.context.annotation.Profile;

@Configuration
@Profile("uat")
public class UatSystemConfiguration implements SystemConfiguration {


    private String workstationimagemodule =  "/var/www/html/befit/img/tvs/";
    private String baseurl = "http://befit.squapl.com";

    private String[] allowDomain = {"https://myprofitgym.com", "https://www.myprofitgym.com", "http://localhost:8080", "https://www.befit.squapl.com", "https://befit.squapl.com", "http://www.befit.squapl.com", "https://befit.squapl.com:8080", "http://befit.squapl.com:8080", "http://befit.squapl.com", "www.befit.squapl.com", "http://ec2-52-77-210-225.ap-southeast-1.compute.amazonaws.com"};

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
