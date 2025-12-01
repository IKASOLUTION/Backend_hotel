package com.hotel.bf.dto;

import com.hotel.bf.domain.enums.StatutSejour;
import com.hotel.bf.domain.enums.TypeDocument;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 * A sejours.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class SejoursDto extends AbstractAuditEntityDto {
    private Long id;

    private String nomClient;

    private String prenomClient;

    private LocalDate dateDaissance;

    private String lieuDaissance;

    private TypeDocument typeDocument;

    private String numeroDocument;

    private String profession;

    private String lieuResidence;

    private String motifSejour;

    private String numeroChambre;

    private String telephone;

    private LocalDateTime dateEntree;

    private LocalDateTime dateSortie;

    private String observation;

    private String photo;

    private String signature;

    private Boolean synchronise;

    private StatutSejour statut;

    private MultipartFile photoFile;

    private MultipartFile signatureFile;

    private NationaliteDto nationalite;

    private UserDto agentEntree;

    private UserDto agentSortie;

    private HotelDto hotel;

    private RegionDto region;

    private ProvinceDto province;

    private CommuneDto commune;
}
