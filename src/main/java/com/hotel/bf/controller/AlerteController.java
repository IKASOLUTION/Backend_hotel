package com.hotel.bf.controller;

import com.hotel.bf.dto.AlerteDto;
import com.hotel.bf.service.AlerteService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tags(@Tag(name = "Alerte", description = "Gestion des alertes"))
public class AlerteController {
    private final AlerteService alerteService;

    /**
     * POST  /alertes  : Creates a new alertes.
     *
     * @param dto {@link AlerteDto}
     * @return {@link AlerteDto}
     */
    @PostMapping("/alertes")
    @Operation(summary = "Creating a new alertes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${swagger.http-status.200}"),
            @ApiResponse(responseCode = "404", description = "${swagger.http-status.404}"),
            @ApiResponse(responseCode = "500", description = "${swagger.http-status.500}")
    })
    public ResponseEntity<AlerteDto> create(@Valid @RequestBody final AlerteDto dto) {
        
        return ResponseEntity.ok(alerteService.create(dto));
    }

    /**
     * PUT  /alertes/:id  : Updates an existing Alerte.
     *
     * @param dto
     * @param id
     * @return {@link AlerteDto}
     */
    @PutMapping("/alertes/{id}")
    @Operation(summary = "Update an existing alertes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${swagger.http-status.200}"),
            @ApiResponse(responseCode = "400", description = "${swagger.http-status.400}"),
            @ApiResponse(responseCode = "404", description = "${swagger.http-status.404}"),
            @ApiResponse(responseCode = "409", description = "${swagger.http-status.409}"),
            @ApiResponse(responseCode = "500", description = "${swagger.http-status.500}")
    })
    public ResponseEntity<AlerteDto> update(@Valid @RequestBody final AlerteDto dto,
                                              @PathVariable Long id) {
        return ResponseEntity.ok(alerteService.update(dto, id));
    }

    /**
     * GET /:id : get alertes.
     *
     * @param id
     * @return {@link List<AlerteDto>}
     */
    @GetMapping("/alertes/{id}")
    @Operation(summary = "Get alertes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${swagger.http-status.200}"),
            @ApiResponse(responseCode = "404", description = "${swagger.http-status.404}"),
            @ApiResponse(responseCode = "500", description = "${swagger.http-status.500}")
    })
    public ResponseEntity<AlerteDto> findOne(@PathVariable final Long id) {
        return ResponseEntity.ok(alerteService.findOne(id));
    }

    /**
     * DELETE /:id : delete alertes.
     *
     * @param id
     * @return {@link List<AlerteDto>}
     */
    @DeleteMapping("/alertes/{id}")
    @Operation(summary = "Remove alertes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${swagger.http-status.200}"),
            @ApiResponse(responseCode = "400", description = "${swagger.http-status.400}"),
            @ApiResponse(responseCode = "500", description = "${swagger.http-status.500}")
    })
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        alerteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/alertes/criteria")
    @Operation(summary = "fech by page an existing alertes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${swagger.http-status.200}"),
            @ApiResponse(responseCode = "400", description = "${swagger.http-status.400}"),
            @ApiResponse(responseCode = "404", description = "${swagger.http-status.404}"),
            @ApiResponse(responseCode = "409", description = "${swagger.http-status.409}"),
            @ApiResponse(responseCode = "500", description = "${swagger.http-status.500}")
    })
    public ResponseEntity<List<AlerteDto>> getWithCriteria(final @RequestBody AlerteDto dto , Pageable pageable) {
        Page<AlerteDto> page = alerteService.findByCriteria(dto,pageable);
        return ResponseEntity.ok().body(page.getContent());
    }

}
