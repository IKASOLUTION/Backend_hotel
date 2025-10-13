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
import com.hotel.bf.domain.enums.Civilite;
import com.hotel.bf.domain.enums.Mois;


@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class SouscriptionDto extends AbstractAuditEntityDto{

    private Long id;
    private String cli;
    private String npc;
    private String numeroSouscription;
    private String clc;
    private Civilite civilite;
    private String nomRest;
    private String emails;
    private String tel1;
    private String tel2;
    private Long mois;
    private LocalDateTime dateSouscription;
    private AgenceDto agence;
    private UserDto user;
   
    private String sexe;
    // private Boolean deleted;

    
}
