package com.hotel.bf.repository;

import java.util.List;

import com.hotel.bf.domain.MenuAction;

public interface MenuActionRepository extends AbstractRepository<MenuAction, Long> {
    boolean existsByDeletedFalseAndMenuActionLibelleAndIdNot(String libelle, Long id);

    List<MenuAction> findByModuleParamIdAndDeletedFalse(Long moduleId);
    boolean existsByDeletedFalseAndMenuActionCodeAndIdNot(String code, Long id);
    boolean existsByDeletedFalseAndMenuActionCodeIgnoreCase(String code);
    List<MenuAction> findByDeletedFalseAndMenuActionCodeIgnoreCase(String code);
    
}
