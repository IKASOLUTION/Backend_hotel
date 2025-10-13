package com.hotel.bf.repository;

import java.util.List;

import com.hotel.bf.domain.Mail;

public interface MailRepository extends AbstractRepository<Mail, Long> {
  
    List<Mail> findAllByDeletedFalse();
    List<Mail> findAllByDeletedFalseAndMaillingId(Long id);

}
