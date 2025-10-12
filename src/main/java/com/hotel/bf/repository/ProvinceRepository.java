package com.hotel.bf.repository;

import com.hotel.bf.domain.Province;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
@Repository
public interface ProvinceRepository extends JpaRepository<Province, Long> {
    Optional<Province> findByLibelleAndDeletedFalse(String libelle);

    @Query("select p from Province p where p.deleted =false " +
            "and (:region  IS NULL OR p.region.id =:region)" +
            " and (:libelle  IS NULL OR :libelle='' OR p.libelle LIKE CONCAT('%', CAST(:libelle AS string), '%')) ")
    Page<Province> findWithCriteria(Long region ,String libelle, Pageable pageable);

    List<Province> findAllByDeletedIsFalse();

}
