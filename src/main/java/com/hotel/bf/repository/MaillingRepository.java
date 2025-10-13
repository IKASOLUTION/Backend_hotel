package com.hotel.bf.repository;

import java.time.LocalDate;
import java.util.List;

import com.hotel.bf.domain.Mailling;

public interface MaillingRepository extends AbstractRepository<Mailling, Long> {
  
    List<Mailling> findAllByDeletedFalse();
    List<Mailling> findByDateBetweenAndDeletedFalse(LocalDate dateD,LocalDate dateF);
;

}
