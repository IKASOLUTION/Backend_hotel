package com.hotel.bf.service;

import com.hotel.bf.config.audit.EntityAuditAction;
import com.hotel.bf.config.audit.ObjetEntity;
import com.hotel.bf.domain.Nationalite;
import com.hotel.bf.dto.NationaliteDto;
import com.hotel.bf.dto.mapper.NationaliteMapper;
import com.hotel.bf.repository.NationaliteRepository;
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
public class NationaliteService {
    private final NationaliteRepository nationaliteRepository;
    private final NationaliteMapper nationaliteMapper;
    private final TraceService traceService;


    /**
     * Save nationalite.
     *
     * @param dto {@link NationaliteDto}
     * @return saved ticket object
     */
    public NationaliteDto create(final NationaliteDto dto) {
        if(nationaliteRepository.findByLibelleIgnoreCaseAndDeletedFalse(dto.getLibelle()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La nationalite "+dto.getLibelle()+ " existe déjà");
        }
        Nationalite nationalite = nationaliteMapper.toEntity(dto);
        nationalite= nationaliteRepository.save(nationalite);
        return nationaliteMapper.toDto(nationalite);
    }

    /**
     * Update existing Nationalite.
     *
     * @param dto {@link NationaliteDto}
     * @return updated region object
     */
    public NationaliteDto update(final NationaliteDto dto, final long id) {
        if (!nationaliteRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No nationalite exists with this ID : %d", id));
        }

        if (Objects.isNull(dto.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already created filiale cannot have null ID.");
        }
        Nationalite nationalite = nationaliteMapper.toEntity(dto);
        return nationaliteMapper.toDto(nationaliteRepository.save(nationalite));
    }

    /**
     * Get Nationalite by id.
     *
     * @param id searched ticket id
     * @return found Nationalite object
     */
    public NationaliteDto findOne(final long id) {
        if (!nationaliteRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No nationalite exists with this ID : %d", id));
        }
        return nationaliteMapper.toDto(nationaliteRepository.findById(id).orElse(null));
    }

    /**
     * Fetch page Nationalite stored in DB.
     * @param dto of {@link NationaliteDto}
     * @return page of {@link NationaliteDto}
     */
    public Page<NationaliteDto> findByPage(final NationaliteDto dto, Pageable pageable) {
        return nationaliteRepository.findWithCriteria(dto.getCode(), dto.getLibelle(), pageable).map(nationaliteMapper::toDto);

    }

    /**
     * Fetch all Nationalite stored in DB.
     * @return list of {@link NationaliteDto}
     */
    public List<NationaliteDto> findAll() {
        return nationaliteRepository.findAllByDeletedIsFalse().stream().map(nationaliteMapper::toDto).toList();

    }

    /**
     * Remove a Nationalite by id if exists.
     *
     * @param id removed ticket id.
     */
    public void delete(final long id) {
        nationaliteRepository.findById(id).ifPresentOrElse(nationalite -> {
            nationalite.setDeleted(Boolean.TRUE);
            nationaliteRepository.save(nationalite);
            traceService.writeAuditEvent( EntityAuditAction.DELETE, ObjetEntity.NATIONALITE);
        }, () -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Cannot remove region with ID : %d", id));
        });
    }

}
