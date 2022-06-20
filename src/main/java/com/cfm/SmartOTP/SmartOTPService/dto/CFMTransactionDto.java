package com.cfm.SmartOTP.SmartOTPService.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.time.Instant;

@Getter
@Setter
public class CFMTransactionDto {
    private Long ID;
    private String BRANDNAME;
    private Integer TYPE;
    private String USERID;
    private String CONTENT;
    private String APPCODE;
    private String EXTRAINFO;
    private BigInteger STATUSID;
    private String SECRETKEY;
    private String REMARK;
    private String CREATEDBY;
    private Instant CREATEDDATE;
    private String UPDATEDBY;
    private Instant UPDATEDDATE;
}
