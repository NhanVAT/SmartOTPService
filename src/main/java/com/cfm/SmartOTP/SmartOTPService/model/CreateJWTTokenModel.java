package com.cfm.SmartOTP.SmartOTPService.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class CreateJWTTokenModel {
    @NotEmpty(message = "userID not empty")
    private String userId;

    @NotEmpty(message = "appCode not empty")
    private String appCode;

    @NotEmpty(message = "appKey not empty")
    private String appKey;
}
