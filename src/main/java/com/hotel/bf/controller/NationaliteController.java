package com.hotel.bf.controller;

import com.hotel.bf.dto.NationaliteDto;
import com.hotel.bf.dto.RegionDto;
import com.hotel.bf.service.NationaliteService;
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
@Tags(@Tag(name = "Nationalite", description = "Gestion des nationalites"))
public class NationaliteController {
    private final NationaliteService nationaliteService;

    /**
     * POST  /nationalites  : Creates a new nationalites.
     *
     * @param dto {@link NationaliteDto}
     * @return {@link NationaliteDto}
     */
    @PostMapping("/nationalites")
    @Operation(summary = "Creating a new nationalites.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${swagger.http-status.200}"),
            @ApiResponse(responseCode = "404", description = "${swagger.http-status.404}"),
            @ApiResponse(responseCode = "500", description = "${swagger.http-status.500}")
    })
    public ResponseEntity<NationaliteDto> create(@Valid @RequestBody final NationaliteDto dto) {
        
        return ResponseEntity.ok(nationaliteService.create(dto));
    }

    /**
     * PUT  /nationalites/:id  : Updates an existing nationalites.
     *
     * @param dto
     * @param id
     * @return {@link NationaliteDto}
     */
    @PutMapping("/nationalites/{id}")
    @Operation(summary = "Update an existing nationalites.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${swagger.http-status.200}"),
            @ApiResponse(responseCode = "400", description = "${swagger.http-status.400}"),
            @ApiResponse(responseCode = "404", description = "${swagger.http-status.404}"),
            @ApiResponse(responseCode = "409", description = "${swagger.http-status.409}"),
            @ApiResponse(responseCode = "500", description = "${swagger.http-status.500}")
    })
    public ResponseEntity<NationaliteDto> update(@Valid @RequestBody final NationaliteDto dto, @PathVariable Long id) {
        return ResponseEntity.ok(nationaliteService.update(dto, id));
    }

    /**
     * GET / : get all nationalites.
     *
     * @return {@link List<NationaliteDto>}
     */
    @GetMapping("/nationalites")
    @Operation(summary = "Fetch all nationalites")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${swagger.http-status.200}"),
            @ApiResponse(responseCode = "204", description = "${swagger.http-status.204}"),
            @ApiResponse(responseCode = "500", description = "${swagger.http-status.500}")
    })
    public ResponseEntity<List<NationaliteDto>> getAll() {
        return new ResponseEntity<>(nationaliteService.findAll(), HttpStatus.OK);
    }

    /**
     * GET /:id : get nationalites.
     *
     * @param id
     * @return {@link List<NationaliteDto>}
     */
    @GetMapping("/nationalites/{id}")
    @Operation(summary = "Get nationalites")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${swagger.http-status.200}"),
            @ApiResponse(responseCode = "404", description = "${swagger.http-status.404}"),
            @ApiResponse(responseCode = "500", description = "${swagger.http-status.500}")
    })
    public ResponseEntity<NationaliteDto> findOne(@PathVariable final Long id) {
        return ResponseEntity.ok(nationaliteService.findOne(id));
    }

    /**
     * DELETE /:id : delete nationalites.
     *
     * @param id
     * @return {@link List<NationaliteDto>}
     */
    @DeleteMapping("/nationalites/{id}")
    @Operation(summary = "Remove nationalites")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${swagger.http-status.200}"),
            @ApiResponse(responseCode = "400", description = "${swagger.http-status.400}"),
            @ApiResponse(responseCode = "500", description = "${swagger.http-status.500}")
    })
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        nationaliteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/nationalites/criteria")
    @Operation(summary = "fech by page an existing nationalites.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${swagger.http-status.200}"),
            @ApiResponse(responseCode = "400", description = "${swagger.http-status.400}"),
            @ApiResponse(responseCode = "404", description = "${swagger.http-status.404}"),
            @ApiResponse(responseCode = "409", description = "${swagger.http-status.409}"),
            @ApiResponse(responseCode = "500", description = "${swagger.http-status.500}")
    })
    public ResponseEntity<List<NationaliteDto>> getWithCriteria(final @RequestBody NationaliteDto dto , Pageable pageable) {
        Page<NationaliteDto> page = nationaliteService.findByPage(dto,pageable);
        return ResponseEntity.ok().body(page.getContent());
    }

}
