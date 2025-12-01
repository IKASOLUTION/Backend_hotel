package com.hotel.bf.domain;

import com.hotel.bf.domain.enums.StatutSejour;
import com.hotel.bf.domain.enums.TypeDocument;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 * A sejours.
 */
@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sejours")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class Sejours extends AbstractAuditEntity  implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sejours_seq_generator")
    @SequenceGenerator(name = "sejours_seq_generator", sequenceName = "sejours_sequence",
            initialValue = 1001, allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "nom_client", nullable = false)
    private String nomClient;

    @NotNull
    @Column(name = "prenom_client", nullable = false)
    private String prenomClient;

    @Column(name = "date_naissance")
    private LocalDate dateDaissance;

    @Column(name = "lieu_naissance")
    private String lieuDaissance;

   @Enumerated(EnumType.STRING)
    @Column(name = "type_document")
    private TypeDocument typeDocument;

    @Column(name = "numero_document")
    private String numeroDocument;

    @Column(name = "profession")
    private String profession;

    @Column(name = "lieu_residence")
    private String lieuResidence;

    @Column(name = "motif_sejour", length = 1024)
    private String motifSejour;

    @Column(name = "numero_chambre", length = 10)
    private String numeroChambre;

    @Column(name = "telephone", length = 20)
    private String telephone;

    @Column(name = "date_entree")
    private LocalDateTime dateEntree;

    @Column(name = "date_sortie")
    private LocalDateTime dateSortie;

    @Column(name = "observation", length = 1024)
    private String observation;

    @Column(name = "photo")
    private String photo;

    @Column(name = "signature")
    private String signature;

    @Column(name = "synchronise")
    private Boolean synchronise = Boolean.FALSE;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut")
    private StatutSejour statut;

    @ManyToOne(fetch = FetchType.LAZY)
    private Nationalite nationalite;

    @ManyToOne(fetch = FetchType.LAZY)
    private User agentEntree;

    @ManyToOne(fetch = FetchType.LAZY)
    private User agentSortie;

    @ManyToOne(fetch = FetchType.LAZY)
    private Hotel hotel;

    @ManyToOne(fetch = FetchType.LAZY)
    private Region region;

    @ManyToOne(fetch = FetchType.LAZY)
    private Province province;

    @ManyToOne(fetch = FetchType.LAZY)
    private Commune commune;
}
