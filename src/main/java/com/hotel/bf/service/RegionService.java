package com.hotel.bf.service;

import com.hotel.bf.config.audit.EntityAuditAction;
import com.hotel.bf.config.audit.ObjetEntity;
import com.hotel.bf.domain.Region;
import com.hotel.bf.dto.RegionDto;
import com.hotel.bf.dto.mapper.RegionMapper;
import com.hotel.bf.repository.RegionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class RegionService {
    private final RegionRepository regionRepository;
    private final RegionMapper regionMapper;
    private final TraceService traceService;


    /**
     * Save region.
     *
     * @param dto {@link RegionDto}
     * @return saved ticket object
     */
    public RegionDto create(final RegionDto dto) {
        if (regionRepository.findByCodeAndDeletedFalse(dto.getCode()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Le code "+dto.getCode()+ " existe déjà");
        }

        if(regionRepository.findByLibelleAndDeletedFalse(dto.getLibelle()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La region "+dto.getLibelle()+ " existe déjà");
        }
        Region region = regionMapper.toEntity(dto);
        region= regionRepository.save(region);
        return regionMapper.toDto(region);
    }

    /**
     * Update existing region.
     *
     * @param dto {@link RegionDto}
     * @return updated region object
     */
    public RegionDto update(final RegionDto dto, final long id) {
        if (!regionRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No region exists with this ID : %d", id));
        }

        if (Objects.isNull(dto.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already created filiale cannot have null ID.");
        }
        Region region = regionMapper.toEntity(dto);
        return regionMapper.toDto(regionRepository.save(region));
    }

    /**
     * Get region by id.
     *
     * @param id searched ticket id
     * @return found region object
     */
    public RegionDto findOne(final long id) {
        if (!regionRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No region exists with this ID : %d", id));
        }
        return regionMapper.toDto(regionRepository.findById(id).orElse(null));
    }

    /**
     * Fetch page region stored in DB.
     * @param dto of {@link RegionDto}
     * @return page of {@link RegionDto}
     */
    public Page<RegionDto> findByPage(RegionDto dto, Pageable pageable) {
        return regionRepository.findWithCriteria(dto.getCode(), dto.getLibelle(), pageable).map(regionMapper::toDto);

    }

    /**
     * Fetch all region stored in DB.
     * @return list of {@link RegionDto}
     */
    public List<RegionDto> findAll() {
        return regionRepository.findAllByDeletedIsFalse().stream().map(regionMapper::toDto).toList();

    }

    /**
     * Remove a region by id if exists.
     *
     * @param id removed ticket id.
     */
    public void delete(final long id) {
        regionRepository.findById(id).ifPresentOrElse(region -> {
            region.setDeleted(Boolean.TRUE);
            regionRepository.save(region);
            traceService.writeAuditEvent( EntityAuditAction.DELETE, ObjetEntity.REGION);
        }, () -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Cannot remove region with ID : %d", id));
        });
    }

}
