package com.cfm.SmartOTP.SmartOTPService.repository;

import com.cfm.SmartOTP.SmartOTPService.entity.CFMApiAccount;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CFMApiAccontRepository extends BaseRepository<CFMApiAccount, Long> {
    List<CFMApiAccount> findByAppcodeAndAppkeyAndIsactive(String appcode, String appkey, Integer isactive);
}
