package com.hotel.bf.dto;

import com.hotel.bf.domain.AbstractAuditEntity;
import com.hotel.bf.domain.enums.NiveauAlerte;
import com.hotel.bf.domain.enums.Statut;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import java.time.LocalDate;


/**
 * A hotel.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class ListeNoireDto extends AbstractAuditEntity {

    private Long id;
    /**
     * Nom du client
     */

    private String nom;

    /**
     * Prenom du client
     */

    private String prenom;

    /**
     * Surnom du client
     */

    private String surnom;

    /**
     * Date de naissance du client
     */
    private LocalDate dateNaissance;

    /**
     * Lieu de naissance du client
     */
    private String lieuNaissance;

    /**
     * Nationalite du client
     */
    private String nationalite;

    /**
     * Type document du client fourni
     */
    private String typeDocument;

    /**
     * Numero document du client fourni
     */
    private String numeroDocument;

    /**
     * Motif de recherche du client fourni
     */
    private String motifRecherche;

    /**
     * Chemin du photo du client fourni
     */
    private String photo;

    /**
     * Type d'alerte
     */
    private NiveauAlerte niveauAlerte;

    /**
     * Statut
     */
     private Statut statut;
  
}
