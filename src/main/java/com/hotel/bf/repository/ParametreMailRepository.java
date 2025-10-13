package com.hotel.bf.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.bf.domain.ParametreMail;


public interface ParametreMailRepository extends JpaRepository<ParametreMail, Long> {
   
    boolean existsByCode(Long code);
    ParametreMail findByCode(Long code);

}
