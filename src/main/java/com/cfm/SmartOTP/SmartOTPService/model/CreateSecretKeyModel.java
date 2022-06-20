package com.cfm.SmartOTP.SmartOTPService.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class CreateSecretKeyModel {
    @NotEmpty(message = "userID not empty")
    private String userId;

    private String regionCode;

    @NotEmpty(message = "appCode not empty")
    private String appCode;

    @NotEmpty(message = "appKey not empty")
    private String appKey;

    private String extraInfo;

    @NotEmpty(message = "publicKey not empty")
    private String publicKey;

    public CreateSecretKeyModel() {
    }
}
