package com.hotel.bf.dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hotel.bf.domain.Commune;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class HotelDto  extends AbstractAuditEntityDto {

    private Long id;
   
    private String denomination;

   
 
    private String email;

  
    private String telephone;

  
   
    private BigDecimal latitude;

  
    private BigDecimal longitude;

  
    private String nom_promoteur;

   
    private String prenom_promoteur;

   
    private String email_promoteur;

   @Lob
    private byte[] logo_hotel;

    private Boolean status;

    private CommuneDto commune;    
   
  

    
}
