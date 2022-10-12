package com.cfm.SmartOTP.SmartOTPService.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "cfm_apiaccount")
@Data
@Entity
@NoArgsConstructor
public class CFMApiAccount extends BaseEntity implements Serializable {

  @Column(name = "isactive", nullable = false)
  private Integer isactive;

  @Column(name = "email", nullable = false)
  private String email;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "appkey", nullable = false)
  private String appkey;

  @Column(name = "appcode", nullable = false)
  private String appcode;

  @Column(name = "remark")
  private String remark;

}
