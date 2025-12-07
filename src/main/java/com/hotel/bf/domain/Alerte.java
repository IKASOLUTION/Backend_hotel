package com.hotel.bf.domain;

import com.hotel.bf.domain.enums.StatutAlerte;
import com.hotel.bf.domain.enums.TypeCorrespondance;
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
import java.time.LocalDateTime;


/**
 * A alerte.
 */
@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "alerte")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class Alerte extends AbstractAuditEntity  implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "alerte_seq_generator")
    @SequenceGenerator(name = "alerte_seq_generator", sequenceName = "alerte_sequence",
            initialValue = 1001, allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "niveau_correspondance", nullable = false)
    private Double niveauCorrespondance;


   @Enumerated(EnumType.STRING)
    @Column(name = "type_correspondance")
    private TypeCorrespondance typeCorrespondance;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut_alerte")
    private StatutAlerte statutAlerte;

    @Column(name = "action_prise", length = 1024)
    private String actionPrise;

    @Column(name = "date_alerte")
    private LocalDateTime dateAlerte;

    @Column(name = "date_traitement")
    private LocalDateTime dateTraitement;

    @ManyToOne(fetch = FetchType.LAZY)
    private Hotel hotel;

    @ManyToOne(fetch = FetchType.LAZY)
    private Region region;

    @ManyToOne(fetch = FetchType.LAZY)
    private Province province;

    @ManyToOne(fetch = FetchType.LAZY)
    private Commune commune;

    @ManyToOne(fetch = FetchType.LAZY)
    private ListeNoire listeNoire;

    @ManyToOne(fetch = FetchType.LAZY)
    private Sejours sejours;

    @ManyToOne(fetch = FetchType.LAZY)
    private User userNotifie;

    @ManyToOne(fetch = FetchType.LAZY)
    private User traitePar;
}
