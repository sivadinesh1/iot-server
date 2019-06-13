package com.tvs.iot.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class APIResponse {

    private HttpStatus status;
    private String message;
    private String additionalinfo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;

    public APIResponse(String message, HttpStatus status, String additionalinfo) {
        this.message = message;
        this.status = status;
        this.additionalinfo = additionalinfo;
        timestamp = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getAdditionalInfo() {
        return additionalinfo;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}