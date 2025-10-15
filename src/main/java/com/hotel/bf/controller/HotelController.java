package com.hotel.bf.controller;

import com.hotel.bf.dto.HotelDto;
import com.hotel.bf.dto.ProvinceDto;
import com.hotel.bf.dto.RegionDto;
import com.hotel.bf.service.HotelService;
import com.hotel.bf.service.ProvinceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tags(@Tag(name = "Hotel", description = "Gestion des hotels"))
public class HotelController {
    private final HotelService hotelService;

    /**
     * POST  /hotels  : Creates a new Hotel.
     *
     * @param dto {@link HotelDto}
     * @param file {@link MultipartFile}
     * @return {@link HotelDto}
     */
    @PostMapping("/hotels")
    @Operation(summary = "Creating a new Hotel.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${swagger.http-status.200}"),
            @ApiResponse(responseCode = "404", description = "${swagger.http-status.404}"),
            @ApiResponse(responseCode = "500", description = "${swagger.http-status.500}")
    })
    public ResponseEntity<HotelDto> create(@Valid @RequestPart("dto") final HotelDto dto,
                                           @RequestParam(value = "file",required = false) MultipartFile file) {
        
        return ResponseEntity.ok(hotelService.create(dto,file));
    }

    /**
     * PUT  /hotels/:id  : Updates an existing Hotel.
     *
     * @param dto
     * @param id
     * @param file
     * @return {@link HotelDto}
     */
    @PutMapping("/hotels/{id}")
    @Operation(summary = "Update an existing Hotel.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${swagger.http-status.200}"),
            @ApiResponse(responseCode = "400", description = "${swagger.http-status.400}"),
            @ApiResponse(responseCode = "404", description = "${swagger.http-status.404}"),
            @ApiResponse(responseCode = "409", description = "${swagger.http-status.409}"),
            @ApiResponse(responseCode = "500", description = "${swagger.http-status.500}")
    })
    public ResponseEntity<HotelDto> update(@Valid @RequestPart("dto") final HotelDto dto,
                                              @RequestParam(value = "file",required = false) MultipartFile file,
                                              @PathVariable Long id) {
        return ResponseEntity.ok(hotelService.update(dto, id,file));
    }

    /**
     * GET / : get all Hotels.
     *
     * @return {@link List<HotelDto>}
     */
    @GetMapping("/hotels")
    @Operation(summary = "Fetch all Hotels")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${swagger.http-status.200}"),
            @ApiResponse(responseCode = "204", description = "${swagger.http-status.204}"),
            @ApiResponse(responseCode = "500", description = "${swagger.http-status.500}")
    })
    public ResponseEntity<List<HotelDto>> getAll() {
        return new ResponseEntity<>(hotelService.findAll(), HttpStatus.OK);
    }

    /**
     * GET /:id : get hotels.
     *
     * @param id
     * @return {@link List<HotelDto>}
     */
    @GetMapping("/hotels/{id}")
    @Operation(summary = "Get Hotel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${swagger.http-status.200}"),
            @ApiResponse(responseCode = "404", description = "${swagger.http-status.404}"),
            @ApiResponse(responseCode = "500", description = "${swagger.http-status.500}")
    })
    public ResponseEntity<HotelDto> findOne(@PathVariable final Long id) {
        return ResponseEntity.ok(hotelService.findOne(id));
    }

    /**
     * DELETE /:id : delete Hotel.
     *
     * @param id
     * @return {@link List<HotelDto>}
     */
    @DeleteMapping("/hotels/{id}")
    @Operation(summary = "Remove Hotel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${swagger.http-status.200}"),
            @ApiResponse(responseCode = "400", description = "${swagger.http-status.400}"),
            @ApiResponse(responseCode = "500", description = "${swagger.http-status.500}")
    })
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        hotelService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/hotels/criteria")
    @Operation(summary = "fech by page an existing Hotel.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${swagger.http-status.200}"),
            @ApiResponse(responseCode = "400", description = "${swagger.http-status.400}"),
            @ApiResponse(responseCode = "404", description = "${swagger.http-status.404}"),
            @ApiResponse(responseCode = "409", description = "${swagger.http-status.409}"),
            @ApiResponse(responseCode = "500", description = "${swagger.http-status.500}")
    })
    public ResponseEntity<List<HotelDto>> getWithCriteria(final HotelDto dto , Pageable pageable) {
        Page<HotelDto> page = hotelService.findByPage(dto,pageable);
        return ResponseEntity.ok().body(page.getContent());
    }

}
