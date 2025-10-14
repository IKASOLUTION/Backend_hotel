package com.hotel.bf.service;

import com.hotel.bf.config.audit.EntityAuditAction;
import com.hotel.bf.config.audit.ObjetEntity;
import com.hotel.bf.domain.Province;
import com.hotel.bf.dto.ProvinceDto;
import com.hotel.bf.dto.mapper.ProvinceMapper;
import com.hotel.bf.repository.ProvinceRepository;
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
public class ProvinceService {
    private final ProvinceRepository provinceRepository;
    private final ProvinceMapper provinceMapper;
    private final TraceService traceService;


    /**
     * Save province.
     *
     * @param dto {@link ProvinceDto}
     * @return saved ticket object
     */
    public ProvinceDto create(final ProvinceDto dto) {
        if(provinceRepository.findByLibelleAndDeletedFalse(dto.getLibelle()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La province "+dto.getLibelle()+ " existe déjà");
        }
        Province province = provinceMapper.toEntity(dto);
        province= provinceRepository.save(province);
        return provinceMapper.toDto(province);
    }

    /**
     * Update existing province.
     *
     * @param dto {@link ProvinceDto}
     * @return updated ticket object
     */
    public ProvinceDto update(final ProvinceDto dto, final long id) {
        if (!provinceRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No province exists with this ID : %d", id));
        }

        if (Objects.isNull(dto.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already created province cannot have null ID.");
        }
        Province province = provinceMapper.toEntity(dto);
        return provinceMapper.toDto(provinceRepository.save(province));
    }

    /**
     * Get province by id.
     *
     * @param id searched ticket id
     * @return found province object
     */
    public ProvinceDto findOne(final long id) {
        if (!provinceRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No province exists with this ID : %d", id));
        }
        return provinceMapper.toDto(Objects.requireNonNull(provinceRepository.findById(id).orElse(null)));
    }

    /**
     * Fetch page region stored in DB.
     * @param dto of {@link ProvinceDto}
     * @return page of {@link ProvinceDto}
     */
    public Page<ProvinceDto> findByPage(ProvinceDto dto, Pageable pageable) {
        return provinceRepository.findWithCriteria(
                dto.getRegion()!=null ? dto.getRegion().getId() : null, dto.getLibelle(), pageable).map(provinceMapper::toDto);

    }

    /**
     * Fetch all province stored in DB.
     * @return list of {@link ProvinceDto}
     */
    public List<ProvinceDto> findAll() {
        return provinceRepository.findAllByDeletedIsFalse().stream().map(provinceMapper::toDto).toList();

    }

    /**
     * Remove a province by id if exists.
     *
     * @param id removed ticket id.
     */
    public void delete(final long id) {
        provinceRepository.findById(id).ifPresentOrElse(province -> {
            province.setDeleted(Boolean.TRUE);
            provinceRepository.save(province);
            traceService.writeAuditEvent( EntityAuditAction.DELETE, ObjetEntity.PROVINCE);
        }, () -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Cannot remove province with ID : %d", id));
        });
    }

}
