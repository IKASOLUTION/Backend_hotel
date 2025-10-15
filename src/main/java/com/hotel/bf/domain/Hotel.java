package com.hotel.bf.domain;

import com.hotel.bf.domain.enums.Statut;
import jakarta.persistence.*;

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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;



/**
 * A hotel.
 */
@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Hotel")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class Hotel extends AbstractAuditEntity  implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hotel_seq_generator")
    @SequenceGenerator(name = "hotel_seq_generator", sequenceName = "hotel_sequence",
            initialValue = 1001, allocationSize = 1)
    private Long id;

    
    @NotNull
    @Column(name = "denomination", nullable = false, unique = true)
    private String denomination;

   
    @Column(name = "email", nullable = true, unique = true)
    private String email;

    @NotNull
    @Column(name = "telephone", nullable = false, unique = true)
    private String telephone;

  
    @Column(name = "latitude", nullable = true)
    private BigDecimal latitude;

     @Column(name = "longitude", nullable = true)
    private BigDecimal longitude;

    @NotNull
    @Column(name = "nom_promoteur", nullable = false)
    private String nom_promoteur;

     @NotNull
    @Column(name = "prenom_promoteur", nullable = false)
    private String prenom_promoteur;

      @Column(name = "email_promoteur", nullable = true)
    private String email_promoteur;

     @Column(name = "logo_hotel")
    @Lob
    private byte[] logo_hotel;

     @Column(name = "statut", nullable = true)
     @Enumerated(EnumType.STRING)
     private Statut statut;

     @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("hotel")
    private Commune commune;    
  
}
