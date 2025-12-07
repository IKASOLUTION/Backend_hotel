package com.hotel.bf.dto.mapper;

import com.hotel.bf.domain.Alerte;
import com.hotel.bf.domain.Sejours;
import com.hotel.bf.dto.AlerteDto;
import com.hotel.bf.dto.SejoursDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Mapper for the entity Alerte and its DTO AlerteDto.
 */
@Component
public class AlerteMapper {
    @Autowired
    private RegionMapper regionMapper;
    @Autowired
    private SejoursMapper sejoursMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private HotelMapper hotelMapper;
    @Autowired
    private ProvinceMapper provinceMapper;
    @Autowired
    private CommuneMapper communeMapper;
    @Autowired
    private ListeNoireMapper listeNoireMapper;

    public AlerteDto toDto(Alerte dt) {
        return AlerteDto.builder()
                .id(dt.getId())
                .niveauCorrespondance(dt.getNiveauCorrespondance())
                .typeCorrespondance(dt.getTypeCorrespondance())
                .statutAlerte(dt.getStatutAlerte())
                .actionPrise(dt.getActionPrise())
                .dateAlerte(dt.getDateAlerte())
                .dateTraitement(dt.getDateTraitement())
                .hotel(dt.getHotel() != null ? hotelMapper.toDto(dt.getHotel()) : null)
                .region(dt.getRegion() != null ? regionMapper.toDto(dt.getRegion()) : null)
                .region(dt.getRegion() != null ? regionMapper.toDto(dt.getRegion()) : null)
                .province(dt.getProvince() != null ? provinceMapper.toDto(dt.getProvince()) : null)
                .commune(dt.getCommune() != null ? communeMapper.toDto(dt.getCommune()) : null)
                .listeNoire(dt.getListeNoire() != null ? listeNoireMapper.toDto(dt.getListeNoire()) : null)
                .sejours(dt.getSejours() != null ? sejoursMapper.toDto(dt.getSejours()) : null)
                .userNotifie(dt.getUserNotifie() != null ? userMapper.toDto(dt.getUserNotifie()) : null)
                .traitePar(dt.getTraitePar() != null ? userMapper.toDto(dt.getTraitePar()) : null)
                .isDeleted(dt.getDeleted())
                .build();
    }

    public Alerte toEntity(AlerteDto dt) {
        return Alerte.builder()
                .id(dt.getId())
                .id(dt.getId())
                .niveauCorrespondance(dt.getNiveauCorrespondance())
                .typeCorrespondance(dt.getTypeCorrespondance())
                .statutAlerte(dt.getStatutAlerte())
                .actionPrise(dt.getActionPrise())
                .dateAlerte(dt.getDateAlerte())
                .dateTraitement(dt.getDateTraitement())
                .hotel(dt.getHotel() != null ? hotelMapper.toEntity(dt.getHotel()) : null)
                .region(dt.getRegion() != null ? regionMapper.toEntity(dt.getRegion()) : null)
                .region(dt.getRegion() != null ? regionMapper.toEntity(dt.getRegion()) : null)
                .province(dt.getProvince() != null ? provinceMapper.toEntity(dt.getProvince()) : null)
                .commune(dt.getCommune() != null ? communeMapper.toEntity(dt.getCommune()) : null)
                .listeNoire(dt.getListeNoire() != null ? listeNoireMapper.toEntity(dt.getListeNoire()) : null)
                .sejours(dt.getSejours() != null ? sejoursMapper.toEntity(dt.getSejours()) : null)
                .userNotifie(dt.getUserNotifie() != null ? userMapper.toEntity(dt.getUserNotifie()) : null)
                .traitePar(dt.getTraitePar() != null ? userMapper.toEntity(dt.getTraitePar()) : null)
                .deleted(dt.getIsDeleted())
                .build();
    }

    public List<AlerteDto> toDto(List<Alerte> dts) {
        return dts.stream().map(this::toDto).toList();
    }

    public List<Alerte> toEntity(List<AlerteDto> dtos) {
        return dtos.stream().map(this::toEntity).toList();
    }
}
