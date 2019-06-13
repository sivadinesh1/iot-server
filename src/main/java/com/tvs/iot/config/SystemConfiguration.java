package com.tvs.iot.config;

public interface SystemConfiguration {

    public String getProfileImageBaseUploadFolder();
    public String getPaymentProofPicsUploadFolder();


    public String[] getAllowedDomains();

    public String getBaseURL();

    public String getAssessmenttemplatefolder();

    public String getAssessmentoutputfolder();
}
