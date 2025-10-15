package com.hotel.bf.repository;

import com.hotel.bf.domain.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    Optional<Hotel> findByDenominationAndCommuneIdAndDeletedIsFalse(String libelle, Long communeId);

    @Query("select h from Hotel h where h.deleted =false " +
            "and (:commune  IS NULL OR h.commune.id =:commune)" +
            " and (:denomination  IS NULL OR :denomination='' OR h.denomination LIKE CONCAT('%', CAST(:denomination AS string), '%')) " +
            "and (:nomPromoteur  IS NULL OR :nomPromoteur='' OR h.nom_promoteur LIKE CONCAT('%', CAST(:nomPromoteur AS string), '%'))" +
            "and (:prenomPromoteur  IS NULL OR :prenomPromoteur='' OR h.prenom_promoteur LIKE CONCAT('%', CAST(:prenomPromoteur AS string), '%'))" +
            "")
    Page<Hotel> findWithCriteria(Long commune ,String denomination,String nomPromoteur, String prenomPromoteur, Pageable pageable);

    List<Hotel> findAllByDeletedIsFalse();

}
