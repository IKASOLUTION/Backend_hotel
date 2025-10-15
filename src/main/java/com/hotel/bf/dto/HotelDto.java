package com.hotel.bf.dto;
import java.math.BigDecimal;

import com.hotel.bf.domain.enums.Statut;
import jakarta.persistence.Lob;
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

    private Statut statut;

    private CommuneDto commune;    
   
  

    
}
