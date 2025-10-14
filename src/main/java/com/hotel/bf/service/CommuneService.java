package com.hotel.bf.service;

import com.hotel.bf.config.audit.EntityAuditAction;
import com.hotel.bf.config.audit.ObjetEntity;
import com.hotel.bf.domain.Commune;
import com.hotel.bf.dto.CommuneDto;
import com.hotel.bf.dto.ProvinceDto;
import com.hotel.bf.dto.mapper.CommuneMapper;
import com.hotel.bf.repository.CommuneRepository;
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
public class CommuneService {
    private final CommuneRepository communeRepository;
    private final CommuneMapper communeMapper;
    private final TraceService traceService;


    /**
     * Save commune.
     *
     * @param dto {@link CommuneDto}
     * @return saved Commune object
     */
    public CommuneDto create(final CommuneDto dto) {
        if(communeRepository.findByLibelleAndDeletedFalse(dto.getLibelle()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La commune "+dto.getLibelle()+ " existe déjà");
        }
        Commune commune = communeMapper.toEntity(dto);
        commune= communeRepository.save(commune);
        return communeMapper.toDto(commune);
    }

    /**
     * Update existing commune.
     *
     * @param dto {@link CommuneDto}
     * @return updated Commune object
     */
    public CommuneDto update(final CommuneDto dto, final long id) {
        if (!communeRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No commune exists with this ID : %d", id));
        }

        if (Objects.isNull(dto.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already created commune cannot have null ID.");
        }
        Commune commune = communeMapper.toEntity(dto);
        return communeMapper.toDto(communeRepository.save(commune));
    }

    /**
     * Get province by id.
     *
     * @param id searched commune id
     * @return found commune object
     */
    public CommuneDto findOne(final long id) {
        if (!communeRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No commune exists with this ID : %d", id));
        }
        return communeMapper.toDto(Objects.requireNonNull(communeRepository.findById(id).orElse(null)));
    }

    /**
     * Fetch page commune stored in DB.
     * @param dto of {@link ProvinceDto}
     * @return page of {@link ProvinceDto}
     */
    public Page<CommuneDto> findByPage(CommuneDto dto, Pageable pageable) {
        return communeRepository.findWithCriteria(
                dto.getProvince()!=null ? dto.getProvince().getId() : null, dto.getLibelle(), pageable).map(communeMapper::toDto);

    }

    /**
     * Fetch all commune stored in DB.
     * @return list of {@link CommuneDto}
     */
    public List<CommuneDto> findAll() {
        return communeRepository.findAllByDeletedIsFalse().stream().map(communeMapper::toDto).toList();

    }

    /**
     * Remove a commune by id if exists.
     *
     * @param id removed ticket id.
     */
    public void delete(final long id) {
        communeRepository.findById(id).ifPresentOrElse(commune -> {
            commune.setDeleted(Boolean.TRUE);
            communeRepository.save(commune);
            traceService.writeAuditEvent( EntityAuditAction.DELETE, ObjetEntity.COMMUNE);
        }, () -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Cannot remove commune with ID : %d", id));
        });
    }

}
