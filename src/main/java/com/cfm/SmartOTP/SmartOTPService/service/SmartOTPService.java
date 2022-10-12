package com.cfm.SmartOTP.SmartOTPService.service;

import com.cfm.SmartOTP.SmartOTPService.dto.CFMTransactionDto;
import com.cfm.SmartOTP.SmartOTPService.model.CreateJWTTokenModel;
import com.cfm.SmartOTP.SmartOTPService.model.CreateSecretKeyModel;
import com.cfm.SmartOTP.SmartOTPService.model.Response;
import com.cfm.SmartOTP.SmartOTPService.model.VerifyOTPModel;
import java.util.List;

public interface SmartOTPService {

  Response getSecrectKey(CreateSecretKeyModel inputData);

  Response getJWTByUserId(CreateJWTTokenModel inputData);

  Response verifyOTP(VerifyOTPModel inputData);

  List<CFMTransactionDto> getAllTransaction();
}
