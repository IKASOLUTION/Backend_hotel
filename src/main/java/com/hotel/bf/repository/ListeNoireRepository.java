package com.hotel.bf.repository;

import com.hotel.bf.domain.ListeNoire;
import com.hotel.bf.domain.enums.NiveauAlerte;
import com.hotel.bf.domain.enums.Statut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface ListeNoireRepository extends JpaRepository<ListeNoire, Long> {

    @Query("select l from ListeNoire l where l.deleted =false " +
            " and (:nom  IS NULL OR :nom='' OR l.nom LIKE CONCAT('%', CAST(:nom AS string), '%')) " +
            "and (:prenom  IS NULL OR :prenom='' OR l.prenom LIKE CONCAT('%', CAST(:prenom AS string), '%'))" +
            "and (:surnom  IS NULL OR :surnom='' OR l.surnom LIKE CONCAT('%', CAST(:surnom AS string), '%'))" +
            "and (:lieuNaissance  IS NULL OR :lieuNaissance='' OR l.lieuNaissance LIKE CONCAT('%', CAST(:lieuNaissance AS string), '%'))" +
            "and (:numero  IS NULL OR :numero='' OR l.numeroDocument LIKE CONCAT('%', CAST(:numero AS string), '%'))" +
            "and (:niveauAlerte  IS NULL OR :niveauAlerte='' OR l.niveauAlerte =:niveauAlerte)" +
            "and (:statut  IS NULL OR :statut='' OR l.statut =:statut)" +
            "and (:dateNaissance  IS NULL OR l.dateNaissance =:dateNaissance)" +
            "and (:nationalite  IS NULL OR :nationalite='' OR l.nationalite LIKE CONCAT('%', CAST(:nationalite AS string), '%'))" +
            "")
    Page<ListeNoire> findWithCriteria(
            String nom , String prenom, String surnom, String lieuNaissance, String numero, NiveauAlerte niveauAlerte,
            Statut statut, LocalDate dateNaissance, String nationalite,Pageable pageable);

    List<ListeNoire> findAllByDeletedIsFalse();

}
