package com.hotel.bf.controller;

import com.hotel.bf.dto.CommuneDto;
import com.hotel.bf.dto.RegionDto;
import com.hotel.bf.service.CommuneService;
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
@Tags(@Tag(name = "Commune", description = "Gestion des commune"))
public class CommuneController {
    private final CommuneService communeService;

    /**
     * POST  /communes  : Creates a new commune.
     *
     * @param dto {@link CommuneDto}
     * @return {@link CommuneDto}
     */
    @PostMapping("/communes")
    @Operation(summary = "Creating a new commune.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${swagger.http-status.200}"),
            @ApiResponse(responseCode = "404", description = "${swagger.http-status.404}"),
            @ApiResponse(responseCode = "500", description = "${swagger.http-status.500}")
    })
    public ResponseEntity<CommuneDto> create(@Valid @RequestBody final CommuneDto dto) {
        return ResponseEntity.ok(communeService.create(dto));
    }

    /**
     * PUT  /communes/:id  : Updates an existing commune.
     *
     * @param dto
     * @param id
     * @return {@link CommuneDto}
     */
    @PutMapping("/communes/{id}")
    @Operation(summary = "Update an existing province.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${swagger.http-status.200}"),
            @ApiResponse(responseCode = "400", description = "${swagger.http-status.400}"),
            @ApiResponse(responseCode = "404", description = "${swagger.http-status.404}"),
            @ApiResponse(responseCode = "409", description = "${swagger.http-status.409}"),
            @ApiResponse(responseCode = "500", description = "${swagger.http-status.500}")
    })
    public ResponseEntity<CommuneDto> update(@Valid @RequestBody final CommuneDto dto, @PathVariable Long id) {
        return ResponseEntity.ok(communeService.update(dto, id));
    }

    /**
     * GET / : get all communes.
     *
     * @return {@link List<CommuneDto>}
     */
    @GetMapping("/communes")
    @Operation(summary = "Fetch all communes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${swagger.http-status.200}"),
            @ApiResponse(responseCode = "204", description = "${swagger.http-status.204}"),
            @ApiResponse(responseCode = "500", description = "${swagger.http-status.500}")
    })
    public ResponseEntity<List<CommuneDto>> getAll() {
        return new ResponseEntity<>(communeService.findAll(), HttpStatus.OK);
    }

    /**
     * GET /:id : get commune.
     *
     * @param id
     * @return {@link List<CommuneDto>}
     */
    @GetMapping("/communes/{id}")
    @Operation(summary = "Get commune")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${swagger.http-status.200}"),
            @ApiResponse(responseCode = "404", description = "${swagger.http-status.404}"),
            @ApiResponse(responseCode = "500", description = "${swagger.http-status.500}")
    })
    public ResponseEntity<CommuneDto> findOne(@PathVariable final Long id) {
        return ResponseEntity.ok(communeService.findOne(id));
    }

    /**
     * DELETE /:id : delete commune.
     *
     * @param id
     * @return {@link List<RegionDto>}
     */
    @DeleteMapping("/communes/{id}")
    @Operation(summary = "Remove commune")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${swagger.http-status.200}"),
            @ApiResponse(responseCode = "400", description = "${swagger.http-status.400}"),
            @ApiResponse(responseCode = "500", description = "${swagger.http-status.500}")
    })
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        communeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/communes/criteria")
    @Operation(summary = "fech by page an existing commune.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${swagger.http-status.200}"),
            @ApiResponse(responseCode = "400", description = "${swagger.http-status.400}"),
            @ApiResponse(responseCode = "404", description = "${swagger.http-status.404}"),
            @ApiResponse(responseCode = "409", description = "${swagger.http-status.409}"),
            @ApiResponse(responseCode = "500", description = "${swagger.http-status.500}")
    })
    public ResponseEntity<List<CommuneDto>> getWithCriteria(@RequestBody final CommuneDto dto , Pageable pageable) {
        Page<CommuneDto> page = communeService.findByPage(dto,pageable);
        return ResponseEntity.ok().body(page.getContent());
    }

}
