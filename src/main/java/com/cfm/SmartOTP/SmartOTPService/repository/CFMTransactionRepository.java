package com.cfm.SmartOTP.SmartOTPService.repository;

import com.cfm.SmartOTP.SmartOTPService.entity.CFMTransaction;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CFMTransactionRepository extends JpaRepository<CFMTransaction, Long> {

  List<CFMTransaction> findByAppcodeAndUserid(String appcode, String userid);
}
