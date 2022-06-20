package com.cfm.SmartOTP.SmartOTPService.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1l;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    protected Long id;

    @CreatedBy
    @Column(name = "createdby", nullable = false)
    protected String createdby;

    @CreatedDate
    @Column(name = "createddate", nullable = false)
    protected Instant createddate = Instant.now();

    @LastModifiedBy
    @Column(name = "updatedby")
    protected String updatedby;

    @LastModifiedDate
    @Column(name = "updateddate")
    protected Instant updateddate = Instant.now();
}
