package com.hotel.bf.repository;

import java.util.List;

import com.hotel.bf.domain.ModuleParam;

public interface ModuleParamRepository extends AbstractRepository<ModuleParam, Long> {
    boolean existsByDeletedFalseAndModuleParamLibelleAndIdNot(String libelle, Long id);
    boolean existsByDeletedFalseAndModuleParamCodeIgnoreCase(String code);
    List<ModuleParam> findByDeletedFalseAndModuleParamCodeIgnoreCase(String code);
}
