package com.cfm.SmartOTP.SmartOTPService.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdBy", "createdDate", "lastModifiedBy",
    "lastModifiedDate"}, allowGetters = true)
@Data
public abstract class BaseEntity implements Serializable {

  private static final long serialVersionUID = 1l;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  protected Long id;

  @CreatedBy
  @Column(name = "created_by", nullable = false)
  protected String createdBy;

  @CreatedDate
  @Column(name = "created_date", nullable = false)
  protected Instant createdDate = Instant.now();

  @LastModifiedBy
  @Column(name = "last_modified_by")
  protected String lastModifiedBy;

  @LastModifiedDate
  @Column(name = "last_modified_date")
  protected Instant lastModifiedDate = Instant.now();
}
