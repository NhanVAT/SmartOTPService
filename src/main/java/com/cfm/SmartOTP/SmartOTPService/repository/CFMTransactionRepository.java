package com.cfm.SmartOTP.SmartOTPService.repository;

import com.cfm.SmartOTP.SmartOTPService.entity.CFMTransaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CFMTransactionRepository extends BaseRepository<CFMTransaction, Long> {
    List<CFMTransaction> findByAppcodeAndUserid(String appcode, String userid);
}
