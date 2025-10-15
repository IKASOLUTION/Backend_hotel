package com.hotel.bf.service;

import com.hotel.bf.config.audit.EntityAuditAction;
import com.hotel.bf.config.audit.ObjetEntity;
import com.hotel.bf.domain.Hotel;
import com.hotel.bf.dto.HotelDto;
import com.hotel.bf.dto.mapper.HotelMapper;
import com.hotel.bf.repository.HotelRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class HotelService {
    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;
    private final TraceService traceService;


    /**
     * Save hotel.
     *
     * @param dto {@link HotelDto}
     * @return saved hotel object
     */
    public HotelDto create(final HotelDto dto, MultipartFile file) {
        try {
            if(hotelRepository.findByDenominationAndCommuneIdAndDeletedIsFalse(
                    dto.getDenomination(),dto.getCommune().getId()).isPresent()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "L'hotel du nom "+dto.getDenomination()+ " existe déjà dans la meme commune");
            }
            Hotel hotel = hotelMapper.toEntity(dto);
            if (!file.isEmpty()) {
                hotel.setLogo_hotel(file.getBytes());
            }
            hotel= hotelRepository.save(hotel);
            return hotelMapper.toDto(hotel);
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
    public HotelDto update(final HotelDto dto, final long id, MultipartFile file) {

        try {
            if (!hotelRepository.existsById(id)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No hotel exists with this ID : %d", id));
            }
            if (Objects.isNull(dto.getId())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already created hotel cannot have null ID.");
            }
            Hotel hotel = hotelMapper.toEntity(dto);
            if (!file.isEmpty()) {
                hotel.setLogo_hotel(file.getBytes());
            }
            return hotelMapper.toDto(hotelRepository.save(hotel));
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
    public HotelDto findOne(final long id) {
        if (!hotelRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No hotel exists with this ID : %d", id));
        }
        return hotelMapper.toDto(Objects.requireNonNull(hotelRepository.findById(id).orElse(null)));
    }

    /**
     * Fetch page hotel stored in DB.
     * @param dto of {@link HotelDto}
     * @return page of {@link HotelDto}
     */
    public Page<HotelDto> findByPage(HotelDto dto, Pageable pageable) {
        return hotelRepository.findWithCriteria(
                dto.getCommune()!=null ? dto.getCommune().getId() : null,
                dto.getDenomination(), dto.getNom_promoteur(), dto.getPrenom_promoteur(), pageable).map(hotelMapper::toDto);

    }

    /**
     * Fetch all hotel stored in DB.
     * @return list of {@link HotelDto}
     */
    public List<HotelDto> findAll() {
        return hotelRepository.findAllByDeletedIsFalse().stream().map(hotelMapper::toDto).toList();

    }

    /**
     * Remove a Hotel by id if exists.
     *
     * @param id removed hotel id.
     */
    public void delete(final long id) {
        hotelRepository.findById(id).ifPresentOrElse(hotel -> {
            hotel.setDeleted(Boolean.TRUE);
            hotelRepository.save(hotel);
            traceService.writeAuditEvent( EntityAuditAction.DELETE, ObjetEntity.PROVINCE);
        }, () -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Cannot remove hotel with ID : %d", id));
        });
    }

}
