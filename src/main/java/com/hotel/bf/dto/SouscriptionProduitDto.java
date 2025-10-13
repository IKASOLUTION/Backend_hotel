package com.hotel.bf.dto;


import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import com.hotel.bf.domain.enums.Etat;
import com.hotel.bf.domain.enums.Mois;


@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class SouscriptionProduitDto extends AbstractAuditEntityDto{

    private Long id;
  
    private SouscriptionDto souscription;
    private Etat etat;
    private LocalDateTime dateSouscription;
    private Long clientNum;

    
}
