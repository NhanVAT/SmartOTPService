package com.cfm.SmartOTP.SmartOTPService.repository;

import com.cfm.SmartOTP.SmartOTPService.entity.CFMApiAccount;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CFMApiAccontRepository extends JpaRepository<CFMApiAccount, Long> {

  List<CFMApiAccount> findByAppcodeAndAppkeyAndIsactive(String appcode, String appkey,
      Integer isactive);
}
