package com.cfm.SmartOTP.SmartOTPService.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.NoRepositoryBean;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Transactional
@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor {
    @Override
    List<T> findAll();

    @Override
    List<T> findAll(Sort var1);

    @Override
    long count();

    @Override
    Page<T> findAll(Pageable page);

    @Override
    <S extends T> Optional<S> findOne(Example<S> example);

}
