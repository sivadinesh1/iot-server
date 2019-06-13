package com.tvs.iot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("uat")
public class UatSystemConfiguration implements SystemConfiguration {

    private String profileimagebaseuploadfolder = "/var/www/html/befit/img/profiles/";

    private String paymentproofpicsuploadfolder =  "/var/www/html/befit/img/enrolments/";

    private String assessmenttemplatefolder =  "/var/www/html/befit/assessments/";
    private String assessmentoutputfolder =  "/var/www/html/befit/assessments/";

    private String baseurl = "http://demo.squapl.com";

    private String[] allowDomain = { "http://localhost:8080", "http://www.demo.squapl.com", "http://demo.squapl.com",  "http://demo.squapl.com:8080",  "http://ec2-13-232-224-38.ap-southeast-1.compute.amazonaws.com"};

    public String[] getAllowedDomains() {
        return this.allowDomain;
    }


    public String getBaseURL() {
        return this.baseurl;
    }



    public String getProfileImageBaseUploadFolder() {
        return this.profileimagebaseuploadfolder;
    }



    public String getPaymentProofPicsUploadFolder() {
        return this.paymentproofpicsuploadfolder;
    }

    public String getAssessmenttemplatefolder() { return this.assessmenttemplatefolder; }

    public String getAssessmentoutputfolder() { return this.assessmentoutputfolder; }


}
