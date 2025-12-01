package com.hotel.bf.service;

import com.hotel.bf.config.audit.EntityAuditAction;
import com.hotel.bf.config.audit.ObjetEntity;
import com.hotel.bf.domain.Sejours;
import com.hotel.bf.dto.HotelDto;
import com.hotel.bf.dto.SejoursDto;
import com.hotel.bf.dto.mapper.SejoursMapper;
import com.hotel.bf.dto.searchSpecification.SejoursSpecification;
import com.hotel.bf.repository.SejoursRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class SejoursService {
    private final SejoursRepository sejoursRepository;
    private final SejoursMapper sejoursMapper;
    private final TraceService traceService;
    private final FileService fileService;
    private final SejoursSpecification sejoursSpecification;


    /**
     * Save Sejours.
     *
     * @param dto {@link SejoursDto}
     * @return saved sejours object
     */
    public SejoursDto create(final SejoursDto dto) {
        try {
            Sejours sejours = sejoursMapper.toEntity(dto);
            sejours= sejoursRepository.save(sejours);
            if (!dto.getPhotoFile().isEmpty()) {
                sejours.setPhoto(fileService.uploadFile(dto.getPhotoFile(), sejours.getId()));
            }
            if (!dto.getSignatureFile().isEmpty()) {
                sejours.setSignature(fileService.uploadFile(dto.getSignatureFile(), sejours.getId()));
            }
            sejours= sejoursRepository.save(sejours);
            return sejoursMapper.toDto(sejours);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l’enregistrement du logo", e);
        }
    }

    /**
     * Update existing Hotel.
     *
     * @param dto {@link HotelDto}
     * @return updated Hotel object
     */
    public SejoursDto update(final SejoursDto dto, final long id, MultipartFile file) {

        try {
            if (!sejoursRepository.existsById(id)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No sejoursDto exists with this ID : %d", id));
            }
            if (Objects.isNull(dto.getId())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already created sejoursDto cannot have null ID.");
            }
            Sejours sejours = sejoursMapper.toEntity(dto);
            sejours = sejoursRepository.save(sejours);
            if (!dto.getPhotoFile().isEmpty()) {
                sejours.setPhoto(fileService.uploadFile(dto.getPhotoFile(), sejours.getId()));
            }
            if (!dto.getSignatureFile().isEmpty()) {
                sejours.setSignature(fileService.uploadFile(dto.getSignatureFile(), sejours.getId()));
            }
            sejours= sejoursRepository.save(sejours);
            return sejoursMapper.toDto(sejours);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l’enregistrement du logo", e);

        }
    }

    /**
     * Get Hotel by id.
     *
     * @param id searched Hotel id
     * @return found HotelDto object
     */
    public SejoursDto findOne(final long id) {
        if (!sejoursRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No sejours exists with this ID : %d", id));
        }
        return sejoursMapper.toDto(Objects.requireNonNull(sejoursRepository.findById(id).orElse(null)));
    }

    /**
     * Fetch page hotel stored in DB.
     * @param criteria of {@link HotelDto}
     * @return page of {@link HotelDto}
     */
    public Page<SejoursDto> findByCriteria(SejoursDto criteria, Pageable pageable) {
        Specification<Sejours> spec = sejoursSpecification.buildSpecification(criteria);
        return sejoursRepository.findAll(spec, pageable).map(sejoursMapper::toDto);

    }

    /**
     * Remove a Hotel by id if exists.
     *
     * @param id removed hotel id.
     */
    public void delete(final long id) {
        sejoursRepository.findById(id).ifPresentOrElse(sejour -> {
            sejour.setDeleted(Boolean.TRUE);
            sejoursRepository.save(sejour);
            traceService.writeAuditEvent( EntityAuditAction.DELETE, ObjetEntity.SEJOURS);
        }, () -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Cannot remove sejours with ID : %d", id));
        });
    }

}
