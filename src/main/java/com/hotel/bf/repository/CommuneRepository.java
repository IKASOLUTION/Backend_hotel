package com.hotel.bf.repository;

import com.hotel.bf.domain.Commune;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
@Repository
public interface CommuneRepository extends JpaRepository<Commune, Long> {
    Optional<Commune> findByLibelleAndDeletedFalse(String libelle);

    @Query("select c from Commune c where c.deleted =false " +
            "and (:province  IS NULL OR c.province.id =:province)" +
            " and (:libelle  IS NULL OR :libelle='' OR c.libelle LIKE CONCAT('%', CAST(:libelle AS string), '%')) ")
    Page<Commune> findWithCriteria(Long province ,String libelle, Pageable pageable);

    List<Commune> findAllByDeletedIsFalse();

}
