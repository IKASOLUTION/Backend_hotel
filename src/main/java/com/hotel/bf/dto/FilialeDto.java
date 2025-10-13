package com.hotel.bf.dto;

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

public class FilialeDto extends AbstractAuditEntityDto{
    
    private Long id;

   // @NotNull
    private String pays;

   // @NotNull
    private String codeBanque;
    private String code;
   // @NotNull
    private String nom;
    // private Boolean deleted;
    private String domaine;

    private Boolean deleted;
    
}
