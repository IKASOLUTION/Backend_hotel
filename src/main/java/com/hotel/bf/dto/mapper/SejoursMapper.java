package com.hotel.bf.dto.mapper;

import com.hotel.bf.domain.Sejours;
import com.hotel.bf.dto.SejoursDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Mapper for the entity Sejours and its DTO SejoursDto.
 */
@Component
public class SejoursMapper {
    @Autowired
    private RegionMapper regionMapper;
    @Autowired
    private NationaliteMapper nationaliteMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private HotelMapper hotelMapper;
    @Autowired
    private ProvinceMapper provinceMapper;
    @Autowired
    private CommuneMapper communeMapper;

    public SejoursDto toDto(Sejours dt) {
        return SejoursDto.builder()
                .id(dt.getId())
                .nomClient(dt.getNomClient())
                .prenomClient(dt.getPrenomClient())
                .dateDaissance(dt.getDateDaissance())
                .lieuDaissance(dt.getLieuDaissance())
                .typeDocument(dt.getTypeDocument())
                .numeroDocument(dt.getNumeroDocument())
                .profession(dt.getProfession())
                .lieuResidence(dt.getLieuResidence())
                .motifSejour(dt.getMotifSejour())
                .numeroChambre(dt.getNumeroChambre())
                .telephone(dt.getTelephone())
                .dateEntree(dt.getDateEntree())
                .dateSortie(dt.getDateSortie())
                .observation(dt.getObservation())
                .photo(dt.getPhoto())
                .signature(dt.getSignature())
                .synchronise(dt.getSynchronise())
                .statut(dt.getStatut())
                .nationalite(dt.getNationalite() != null ? nationaliteMapper.toDto(dt.getNationalite()) : null)
                .agentEntree(dt.getAgentEntree() != null ? userMapper.toDto(dt.getAgentEntree()) : null)
                .agentSortie(dt.getAgentSortie() != null ? userMapper.toDto(dt.getAgentSortie()) : null)
                .hotel(dt.getHotel() != null ? hotelMapper.toDto(dt.getHotel()) : null)
                .region(dt.getRegion() != null ? regionMapper.toDto(dt.getRegion()) : null)
                .region(dt.getRegion() != null ? regionMapper.toDto(dt.getRegion()) : null)
                .province(dt.getProvince() != null ? provinceMapper.toDto(dt.getProvince()) : null)
                .commune(dt.getCommune() != null ? communeMapper.toDto(dt.getCommune()) : null)
                .isDeleted(dt.getDeleted())
                .build();
    }

    public Sejours toEntity(SejoursDto dt) {
        return Sejours.builder()
                .id(dt.getId())
                .nomClient(dt.getNomClient())
                .prenomClient(dt.getPrenomClient())
                .dateDaissance(dt.getDateDaissance())
                .lieuDaissance(dt.getLieuDaissance())
                .typeDocument(dt.getTypeDocument())
                .numeroDocument(dt.getNumeroDocument())
                .profession(dt.getProfession())
                .lieuResidence(dt.getLieuResidence())
                .motifSejour(dt.getMotifSejour())
                .numeroChambre(dt.getNumeroChambre())
                .telephone(dt.getTelephone())
                .dateEntree(dt.getDateEntree())
                .dateSortie(dt.getDateSortie())
                .observation(dt.getObservation())
                .photo(dt.getPhoto())
                .signature(dt.getSignature())
                .synchronise(dt.getSynchronise())
                .statut(dt.getStatut())
                .nationalite(dt.getNationalite() != null ? nationaliteMapper.toEntity(dt.getNationalite()) : null)
                .agentEntree(dt.getAgentEntree() != null ? userMapper.toEntity(dt.getAgentEntree()) : null)
                .agentSortie(dt.getAgentSortie() != null ? userMapper.toEntity(dt.getAgentSortie()) : null)
                .hotel(dt.getHotel() != null ? hotelMapper.toEntity(dt.getHotel()) : null)
                .region(dt.getRegion() != null ? regionMapper.toEntity(dt.getRegion()) : null)
                .region(dt.getRegion() != null ? regionMapper.toEntity(dt.getRegion()) : null)
                .province(dt.getProvince() != null ? provinceMapper.toEntity(dt.getProvince()) : null)
                .commune(dt.getCommune() != null ? communeMapper.toEntity(dt.getCommune()) : null)
                .build();
    }

    public List<SejoursDto> toDto(List<Sejours> dts) {
        return dts.stream().map(this::toDto).toList();
    }

    public List<Sejours> toEntity(List<SejoursDto> dtos) {
        return dtos.stream().map(this::toEntity).toList();
    }
}
