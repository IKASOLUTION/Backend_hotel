package com.hotel.bf.controller;

import com.hotel.bf.dto.HotelDto;
import com.hotel.bf.dto.SejoursDto;
import com.hotel.bf.service.HotelService;
import com.hotel.bf.service.SejoursService;
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
@Tags(@Tag(name = "Hotel", description = "Gestion des sejours"))
public class SejoursController {
    private final SejoursService sejoursService;

    /**
     * POST  /sejours  : Creates a new sejours.
     *
     * @param dto {@link SejoursDto}
     * @param file {@link MultipartFile}
     * @return {@link SejoursDto}
     */
    @PostMapping("/sejours")
    @Operation(summary = "Creating a new sejours.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${swagger.http-status.200}"),
            @ApiResponse(responseCode = "404", description = "${swagger.http-status.404}"),
            @ApiResponse(responseCode = "500", description = "${swagger.http-status.500}")
    })
    public ResponseEntity<SejoursDto> create(@Valid @RequestPart("dto") final SejoursDto dto) {
        
        return ResponseEntity.ok(sejoursService.create(dto));
    }

    /**
     * PUT  /sejours/:id  : Updates an existing sejours.
     *
     * @param dto
     * @param id
     * @param file
     * @return {@link SejoursDto}
     */
    @PutMapping("/sejours/{id}")
    @Operation(summary = "Update an existing sejours.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${swagger.http-status.200}"),
            @ApiResponse(responseCode = "400", description = "${swagger.http-status.400}"),
            @ApiResponse(responseCode = "404", description = "${swagger.http-status.404}"),
            @ApiResponse(responseCode = "409", description = "${swagger.http-status.409}"),
            @ApiResponse(responseCode = "500", description = "${swagger.http-status.500}")
    })
    public ResponseEntity<SejoursDto> update(@Valid @RequestPart("dto") final SejoursDto dto,
                                              @RequestParam(value = "file",required = false) MultipartFile file,
                                              @PathVariable Long id) {
        return ResponseEntity.ok(sejoursService.update(dto, id,file));
    }

    /**
     * GET /:id : get sejours.
     *
     * @param id
     * @return {@link List<SejoursDto>}
     */
    @GetMapping("/sejours/{id}")
    @Operation(summary = "Get sejours")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${swagger.http-status.200}"),
            @ApiResponse(responseCode = "404", description = "${swagger.http-status.404}"),
            @ApiResponse(responseCode = "500", description = "${swagger.http-status.500}")
    })
    public ResponseEntity<SejoursDto> findOne(@PathVariable final Long id) {
        return ResponseEntity.ok(sejoursService.findOne(id));
    }

    /**
     * DELETE /:id : delete Hotel.
     *
     * @param id
     * @return {@link List<HotelDto>}
     */
    @DeleteMapping("/sejours/{id}")
    @Operation(summary = "Remove sejours")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${swagger.http-status.200}"),
            @ApiResponse(responseCode = "400", description = "${swagger.http-status.400}"),
            @ApiResponse(responseCode = "500", description = "${swagger.http-status.500}")
    })
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        sejoursService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/sejours/criteria")
    @Operation(summary = "fech by page an existing sejours.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${swagger.http-status.200}"),
            @ApiResponse(responseCode = "400", description = "${swagger.http-status.400}"),
            @ApiResponse(responseCode = "404", description = "${swagger.http-status.404}"),
            @ApiResponse(responseCode = "409", description = "${swagger.http-status.409}"),
            @ApiResponse(responseCode = "500", description = "${swagger.http-status.500}")
    })
    public ResponseEntity<List<SejoursDto>> getWithCriteria(final @RequestBody SejoursDto dto , Pageable pageable) {
        Page<SejoursDto> page = sejoursService.findByCriteria(dto,pageable);
        return ResponseEntity.ok().body(page.getContent());
    }

}
