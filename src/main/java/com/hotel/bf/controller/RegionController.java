package com.hotel.bf.controller;

import com.hotel.bf.dto.RegionDto;
import com.hotel.bf.service.RegionService;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tags(@Tag(name = "Region", description = "Gestion des regions"))
public class RegionController {
    private final RegionService regionService;

    /**
     * POST  /regions  : Creates a new region.
     *
     * @param dto {@link RegionDto}
     * @return {@link RegionDto}
     */
    @PostMapping("/regions")
    @Operation(summary = "Creating a new region.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${swagger.http-status.200}"),
            @ApiResponse(responseCode = "404", description = "${swagger.http-status.404}"),
            @ApiResponse(responseCode = "500", description = "${swagger.http-status.500}")
    })
    public ResponseEntity<RegionDto> create(@Valid @RequestBody final RegionDto dto) {
        
        return ResponseEntity.ok(regionService.create(dto));
    }

    /**
     * PUT  /regions/:id  : Updates an existing region.
     *
     * @param dto
     * @param id
     * @return {@link RegionDto}
     */
    @PutMapping("/regions/{id}")
    @Operation(summary = "Update an existing region.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${swagger.http-status.200}"),
            @ApiResponse(responseCode = "400", description = "${swagger.http-status.400}"),
            @ApiResponse(responseCode = "404", description = "${swagger.http-status.404}"),
            @ApiResponse(responseCode = "409", description = "${swagger.http-status.409}"),
            @ApiResponse(responseCode = "500", description = "${swagger.http-status.500}")
    })
    public ResponseEntity<RegionDto> update(@Valid @RequestBody final RegionDto dto, @PathVariable Long id) {
        return ResponseEntity.ok(regionService.update(dto, id));
    }

    /**
     * GET / : get all regions.
     *
     * @return {@link List<RegionDto>}
     */
    @GetMapping("/regions")
    @Operation(summary = "Fetch all regions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${swagger.http-status.200}"),
            @ApiResponse(responseCode = "204", description = "${swagger.http-status.204}"),
            @ApiResponse(responseCode = "500", description = "${swagger.http-status.500}")
    })
    public ResponseEntity<List<RegionDto>> getAll() {
        return new ResponseEntity<>(regionService.findAll(), HttpStatus.OK);
    }

    /**
     * GET /:id : get region.
     *
     * @param id
     * @return {@link List<RegionDto>}
     */
    @GetMapping("/regions/{id}")
    @Operation(summary = "Get region")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${swagger.http-status.200}"),
            @ApiResponse(responseCode = "404", description = "${swagger.http-status.404}"),
            @ApiResponse(responseCode = "500", description = "${swagger.http-status.500}")
    })
    public ResponseEntity<RegionDto> findOne(@PathVariable final Long id) {
        return ResponseEntity.ok(regionService.findOne(id));
    }

    /**
     * DELETE /:id : delete region.
     *
     * @param id
     * @return {@link List<RegionDto>}
     */
    @DeleteMapping("/regions/{id}")
    @Operation(summary = "Remove region")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${swagger.http-status.200}"),
            @ApiResponse(responseCode = "400", description = "${swagger.http-status.400}"),
            @ApiResponse(responseCode = "500", description = "${swagger.http-status.500}")
    })
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        regionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/regions/criteria")
    @Operation(summary = "fech by page an existing region.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${swagger.http-status.200}"),
            @ApiResponse(responseCode = "400", description = "${swagger.http-status.400}"),
            @ApiResponse(responseCode = "404", description = "${swagger.http-status.404}"),
            @ApiResponse(responseCode = "409", description = "${swagger.http-status.409}"),
            @ApiResponse(responseCode = "500", description = "${swagger.http-status.500}")
    })
    public ResponseEntity<List<RegionDto>> getWithCriteria(@RequestBody final RegionDto dto , Pageable pageable) {
        Page<RegionDto> page = regionService.findByPage(dto,pageable);
        return ResponseEntity.ok().body(page.getContent());
    }

}
