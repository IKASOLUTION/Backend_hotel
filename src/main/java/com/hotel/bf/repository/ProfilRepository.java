package com.hotel.bf.repository;

import com.hotel.bf.domain.Profil;

import java.util.List;

public interface ProfilRepository extends AbstractRepository<Profil, Long> {
    boolean existsByDeletedFalseAndCode(String code);
    boolean existsByDeletedFalseAndCodeAndIdNot(String code, Long id);
    List<Profil> findAllByDeletedFalse();
}
