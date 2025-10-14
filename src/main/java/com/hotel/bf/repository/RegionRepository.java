package com.hotel.bf.repository;

import com.hotel.bf.domain.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
    Optional<Region> findByCodeAndDeletedFalse(String code);

    Optional<Region> findByLibelleAndDeletedFalse(String libelle);

    @Query("select r from Region r where r.deleted =false " +
            "and (:code  IS NULL OR :code='' OR r.code LIKE CONCAT('%', CAST(:code AS string), '%'))" +
            " and (:libelle  IS NULL OR :libelle='' OR r.libelle LIKE CONCAT('%', CAST(:libelle AS string), '%')) ")
    Page<Region> findWithCriteria(String code ,String libelle, Pageable pageable);

    List<Region> findAllByDeletedIsFalse();

}
