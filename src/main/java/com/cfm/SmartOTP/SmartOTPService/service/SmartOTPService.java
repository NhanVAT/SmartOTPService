package com.cfm.SmartOTP.SmartOTPService.service;

import com.cfm.SmartOTP.SmartOTPService.model.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SmartOTPService {
    public Response getSecrectKey(Object inputData) {
        Response result = new Response();
        try {
            result.setMessage("Oki nhe");
            result.setErrorCode("0");
            result.setJwt("12345677");
        } catch (Exception ex) {
            result.setMessage(ex.getMessage());
            result.setErrorCode("1");
        }
        return result;
    }
}
