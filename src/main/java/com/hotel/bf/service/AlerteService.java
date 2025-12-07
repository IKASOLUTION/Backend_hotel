package com.hotel.bf.service;

import com.hotel.bf.config.audit.EntityAuditAction;
import com.hotel.bf.config.audit.ObjetEntity;
import com.hotel.bf.domain.Alerte;
import com.hotel.bf.dto.AlerteDto;
import com.hotel.bf.dto.mapper.AlerteMapper;
import com.hotel.bf.dto.searchSpecification.AlerteSpecification;
import com.hotel.bf.repository.AlerteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class AlerteService {
    private final AlerteRepository alerteRepository;
    private final AlerteMapper alerteMapper;
    private final TraceService traceService;
    private final AlerteSpecification alerteSpecification;


    /**
     * Save Alerte.
     *
     * @param dto {@link AlerteDto}
     * @return saved Alerte object
     */
    public AlerteDto create(final AlerteDto dto) {
        Alerte alerte = alerteMapper.toEntity(dto);
        alerte= alerteRepository.save(alerte);
        return alerteMapper.toDto(alerte);
    }

    /**
     * Update existing Alerte.
     *
     * @param dto {@link AlerteDto}
     * @return updated Alerte object
     */
    public AlerteDto update(final AlerteDto dto, final Long id) {

        if (!alerteRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No Alerte exists with this ID : %d", id));
        }
        if (Objects.isNull(dto.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already created Alerte cannot have null ID.");
        }
        Alerte alerte = alerteMapper.toEntity(dto);
        alerte = alerteRepository.save(alerte);
        return alerteMapper.toDto(alerte);
    }

    /**
     * Get Alerte by id.
     *
     * @param id searched Alerte id
     * @return found Alerte object
     */
    public AlerteDto findOne(final long id) {
        if (!alerteRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No Alerte exists with this ID : %d", id));
        }
        return alerteMapper.toDto(Objects.requireNonNull(alerteRepository.findById(id).orElse(null)));
    }

    /**
     * Fetch page Alerte stored in DB.
     * @param criteria of {@link AlerteDto}
     * @return page of {@link AlerteDto}
     */
    public Page<AlerteDto> findByCriteria(AlerteDto criteria, Pageable pageable) {
        Specification<Alerte> spec = alerteSpecification.buildSpecification(criteria);
        return alerteRepository.findAll(spec, pageable).map(alerteMapper::toDto);

    }

    /**
     * Remove a Alerte by id if exists.
     *
     * @param id removed Alerte id.
     */
    public void delete(final long id) {
        alerteRepository.findById(id).ifPresentOrElse(alerte -> {
            alerte.setDeleted(Boolean.TRUE);
            alerteRepository.save(alerte);
            traceService.writeAuditEvent( EntityAuditAction.DELETE, ObjetEntity.ALERTE);
        }, () -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Cannot remove Alerte with ID : %d", id));
        });
    }

}
