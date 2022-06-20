package com.cfm.SmartOTP.SmartOTPService.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {
    private String errorCode;
    private String message;
    private String jwt;
    private String secretkey;
}
