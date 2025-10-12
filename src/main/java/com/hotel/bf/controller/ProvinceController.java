package com.hotel.bf.controller;

import com.hotel.bf.dto.ProvinceDto;
import com.hotel.bf.dto.RegionDto;
import com.hotel.bf.service.ProvinceService;
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
@Tags(@Tag(name = "Province", description = "Gestion des provinces"))
public class ProvinceController {
    private final ProvinceService provinceService;

    /**
     * POST  /provinces  : Creates a new region.
     *
     * @param dto {@link RegionDto}
     * @return {@link RegionDto}
     */
    @PostMapping("/provinces")
    @Operation(summary = "Creating a new province.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${swagger.http-status.200}"),
            @ApiResponse(responseCode = "404", description = "${swagger.http-status.404}"),
            @ApiResponse(responseCode = "500", description = "${swagger.http-status.500}")
    })
    public ResponseEntity<ProvinceDto> create(@Valid @RequestBody final ProvinceDto dto) {
        
        return ResponseEntity.ok(provinceService.create(dto));
    }

    /**
     * PUT  /provinces/:id  : Updates an existing province.
     *
     * @param dto
     * @param id
     * @return {@link ProvinceDto}
     */
    @PutMapping("/provinces/{id}")
    @Operation(summary = "Update an existing province.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${swagger.http-status.200}"),
            @ApiResponse(responseCode = "400", description = "${swagger.http-status.400}"),
            @ApiResponse(responseCode = "404", description = "${swagger.http-status.404}"),
            @ApiResponse(responseCode = "409", description = "${swagger.http-status.409}"),
            @ApiResponse(responseCode = "500", description = "${swagger.http-status.500}")
    })
    public ResponseEntity<ProvinceDto> update(@Valid @RequestBody final ProvinceDto dto, @PathVariable Long id) {
        return ResponseEntity.ok(provinceService.update(dto, id));
    }

    /**
     * GET / : get all provinces.
     *
     * @return {@link List<ProvinceDto>}
     */
    @GetMapping("/provinces")
    @Operation(summary = "Fetch all provinces")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${swagger.http-status.200}"),
            @ApiResponse(responseCode = "204", description = "${swagger.http-status.204}"),
            @ApiResponse(responseCode = "500", description = "${swagger.http-status.500}")
    })
    public ResponseEntity<List<ProvinceDto>> getAll() {
        return new ResponseEntity<>(provinceService.findAll(), HttpStatus.OK);
    }

    /**
     * GET /:id : get provinces.
     *
     * @param id
     * @return {@link List<ProvinceDto>}
     */
    @GetMapping("/provinces/{id}")
    @Operation(summary = "Get province")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${swagger.http-status.200}"),
            @ApiResponse(responseCode = "404", description = "${swagger.http-status.404}"),
            @ApiResponse(responseCode = "500", description = "${swagger.http-status.500}")
    })
    public ResponseEntity<ProvinceDto> findOne(@PathVariable final Long id) {
        return ResponseEntity.ok(provinceService.findOne(id));
    }

    /**
     * DELETE /:id : delete province.
     *
     * @param id
     * @return {@link List<RegionDto>}
     */
    @DeleteMapping("/provinces/{id}")
    @Operation(summary = "Remove province")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${swagger.http-status.200}"),
            @ApiResponse(responseCode = "400", description = "${swagger.http-status.400}"),
            @ApiResponse(responseCode = "500", description = "${swagger.http-status.500}")
    })
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        provinceService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/provinces/criteria")
    @Operation(summary = "fech by page an existing region.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${swagger.http-status.200}"),
            @ApiResponse(responseCode = "400", description = "${swagger.http-status.400}"),
            @ApiResponse(responseCode = "404", description = "${swagger.http-status.404}"),
            @ApiResponse(responseCode = "409", description = "${swagger.http-status.409}"),
            @ApiResponse(responseCode = "500", description = "${swagger.http-status.500}")
    })
    public ResponseEntity<List<ProvinceDto>> getWithCriteria(@RequestBody final ProvinceDto dto , Pageable pageable) {
        Page<ProvinceDto> page = provinceService.findByPage(dto,pageable);
        return ResponseEntity.ok().body(page.getContent());
    }

}
