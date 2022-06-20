package com.cfm.SmartOTP.SmartOTPService.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigInteger;

@Table(name = "cfm_transaction")
@Data
@Entity
@Getter
@Setter
public class CFMTransaction extends BaseEntity implements Serializable {
    @Column(name = "brandname", nullable = false)
    private String brandname;

    @Column(name = "type", nullable = false)
    private Integer type;

    @Column(name = "userid", nullable = false)
    private String userid;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "regioncode", nullable = false)
    private String regioncode;

    @Column(name = "appcode", nullable = false)
    private String appcode;

    @Column(name = "extrainfo", nullable = false)
    private String extrainfo;

    @Column(name = "statusid", nullable = false)
    private BigInteger statusid;

    @Column(name = "secretkey")
    private String secretkey;

    @Column(name = "remark")
    private String remark;
}
