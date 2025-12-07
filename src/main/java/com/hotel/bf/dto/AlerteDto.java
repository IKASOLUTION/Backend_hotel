package com.hotel.bf.dto;

import com.hotel.bf.domain.enums.StatutAlerte;
import com.hotel.bf.domain.enums.TypeCorrespondance;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;


/**
 * A alerte.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class AlerteDto extends AbstractAuditEntityDto {
    private Long id;

    private Double niveauCorrespondance;

    private TypeCorrespondance typeCorrespondance;

    private StatutAlerte statutAlerte;

    private String actionPrise;

    private LocalDateTime dateAlerte;

    private LocalDateTime dateDebutAlerte;

    private LocalDateTime dateFinAlerte;

    private LocalDateTime dateDebutTraitement;

    private LocalDateTime dateFinTraitement;

    private LocalDateTime dateTraitement;

    private HotelDto hotel;

    private RegionDto region;

    private ProvinceDto province;

    private CommuneDto commune;

    private ListeNoireDto listeNoire;

    private SejoursDto sejours;

    private UserDto userNotifie;

    private UserDto traitePar;
}
