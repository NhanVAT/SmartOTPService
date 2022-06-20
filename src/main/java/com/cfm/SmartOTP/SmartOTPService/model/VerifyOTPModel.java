package com.cfm.SmartOTP.SmartOTPService.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyOTPModel {
    private String appCode;
    private String appKey;
    private String userId;
    private String otpCode;
    private String jwtToken;
}
