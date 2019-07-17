package com.tvs.iot.business;

import lombok.Data;

@Data
public class RFIDTagErrorProcessing {
    private boolean isError;
    private String errReason;
}
