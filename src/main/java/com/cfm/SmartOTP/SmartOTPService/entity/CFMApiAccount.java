package com.cfm.SmartOTP.SmartOTPService.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "cfm_apiaccount")
@Data
@Entity
@Getter
@Setter
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
