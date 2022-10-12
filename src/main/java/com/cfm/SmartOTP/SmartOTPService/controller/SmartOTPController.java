package com.cfm.SmartOTP.SmartOTPService.controller;

import com.cfm.SmartOTP.SmartOTPService.model.CreateJWTTokenModel;
import com.cfm.SmartOTP.SmartOTPService.model.CreateSecretKeyModel;
import com.cfm.SmartOTP.SmartOTPService.model.Response;
import com.cfm.SmartOTP.SmartOTPService.model.VerifyOTPModel;
import com.cfm.SmartOTP.SmartOTPService.service.SmartOTPService;
import io.swagger.annotations.Api;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/smartOTP")
@Api(tags = "smartOTP")
@RequiredArgsConstructor
public class SmartOTPController {

  private final SmartOTPService smartOTPService;

  @PostMapping(value = {"/getSecretKey"}, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Response> getSecretKey(@RequestBody @Valid CreateSecretKeyModel inputData) {

    return ResponseEntity.status(HttpStatus.OK).body(smartOTPService.getSecrectKey(inputData));
  }

  @PostMapping(value = {"/getJWTByUserId"}, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Response> getJWTByUserId(
      @RequestBody @Valid CreateJWTTokenModel inputData) {
    return ResponseEntity.status(HttpStatus.OK).body(smartOTPService.getJWTByUserId(inputData));
  }

  @PostMapping(value = {"/verifyOTP"}, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Response> verifyOTP(@RequestBody @Valid VerifyOTPModel inputData) {
    return ResponseEntity.status(HttpStatus.OK).body(smartOTPService.verifyOTP(inputData));
  }
}
