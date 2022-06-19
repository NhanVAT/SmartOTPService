package com.cfm.SmartOTP.SmartOTPService.controller;

import com.cfm.SmartOTP.SmartOTPService.model.Response;
import com.cfm.SmartOTP.SmartOTPService.service.SmartOTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/com/cfm/smartotp")
public class SmartOTPController {

    @Autowired
    private SmartOTPService smartOTPService;

    @GetMapping("/testAPI")
    public String testAPI() {
        return "Test API success";
    }

    @PostMapping(value = {"getSecretKey"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getSecretKey(@RequestBody Object inputData) {

        return this.createResponse(HttpStatus.OK, smartOTPService.getSecrectKey(inputData));
    }

    ResponseEntity<Object> createResponse(HttpStatus httpStatus, String errorCode, String jwt, String message) {
        Response result = new Response();
        result.setErrorCode(errorCode);
        result.setMessage(message);
        result.setJwt(jwt);

        return new ResponseEntity<>(result, httpStatus);
    }

    ResponseEntity<Object> createResponse(HttpStatus httpStatus, Response response) {
        return new ResponseEntity<>(response, httpStatus);
    }
}
