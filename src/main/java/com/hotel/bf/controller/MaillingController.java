package com.hotel.bf.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.bf.dto.FilialeDto;
import com.hotel.bf.dto.MaillingDto;
import com.hotel.bf.dto.TicketDto;
import com.hotel.bf.service.MaillingService;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tags(@Tag(name = "Mailling", description = "Gestion des mailling"))
public class MaillingController {
    private final MaillingService maillingService;

    /**
     * POST  /tickets  : Creates a new ticket.
     *
     * @param ticketDto
     * @return {@link test.projects.ennov.dto.TicketDto}
     */
    @PostMapping("/maillings")
    @Operation(summary = "Creating a new filiale.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${swagger.http-status.200}"),
            @ApiResponse(responseCode = "404", description = "${swagger.http-status.404}"),
            @ApiResponse(responseCode = "500", description = "${swagger.http-status.500}")
    })
    public ResponseEntity<MaillingDto> create(@Valid @RequestBody final MaillingDto dto) {
        
        return ResponseEntity.ok(maillingService.create(dto));
    }

    /**
     * PUT  /tickets/:id  : Updates an existing Ticket.
     *
     * @param ticketDto
     * @param id
     * @return {@link test.projects.ennov.dto.TicketDto}
     */
    @PutMapping("/maillings/{id}")
    @Operation(summary = "Update an existing ticket.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${swagger.http-status.200}"),
            @ApiResponse(responseCode = "400", description = "${swagger.http-status.400}"),
            @ApiResponse(responseCode = "404", description = "${swagger.http-status.404}"),
            @ApiResponse(responseCode = "409", description = "${swagger.http-status.409}"),
            @ApiResponse(responseCode = "500", description = "${swagger.http-status.500}")
    })
    public ResponseEntity<MaillingDto> updateFiliale(@Valid @RequestBody final MaillingDto dto, @PathVariable Long id) {
        return ResponseEntity.ok(maillingService.update(dto, id));
    }

    /**
     * GET / : get all tickets.
     *
     * @return {@link java.util.List<test.projects.ennov.dto.TicketDto>}
     */
    @PutMapping("/maillings")
    @Operation(summary = "Fetch all tickets")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${swagger.http-status.200}"),
            @ApiResponse(responseCode = "204", description = "${swagger.http-status.204}"),
            @ApiResponse(responseCode = "500", description = "${swagger.http-status.500}")
    })
    public ResponseEntity<List<MaillingDto>> getAllFiliales(@RequestBody  MaillingDto dto) {
        return new ResponseEntity<>(maillingService.findAllFiliale(dto), HttpStatus.OK);
    }

    @GetMapping("/maillings/find-one/{id}")
    @Operation(summary = "Update an existing ticket.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${swagger.http-status.200}"),
            @ApiResponse(responseCode = "400", description = "${swagger.http-status.400}"),
            @ApiResponse(responseCode = "404", description = "${swagger.http-status.404}"),
            @ApiResponse(responseCode = "409", description = "${swagger.http-status.409}"),
            @ApiResponse(responseCode = "500", description = "${swagger.http-status.500}")
    })
    public ResponseEntity<MaillingDto> updateFiliale( @PathVariable Long id) {
        return ResponseEntity.ok(maillingService.findById( id));
    }


}
