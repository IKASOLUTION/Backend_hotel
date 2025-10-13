package com.hotel.bf.controller;
import java.util.List;

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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.hotel.bf.domain.Trace;
import com.hotel.bf.dto.TraceDto;
import com.hotel.bf.service.TraceService;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tags(@Tag(name = "Trace", description = "Gestion des trancabilité"))
public class TraceController {
    
    private final TraceService traceService;

@PutMapping("/traces")
@Operation(summary = "Fetch all produits")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "${swagger.http-status.200}"),
        @ApiResponse(responseCode = "204", description = "${swagger.http-status.204}"),
        @ApiResponse(responseCode = "500", description = "${swagger.http-status.500}")
})
public ResponseEntity<List<TraceDto>> findTraceByPeriode(@RequestBody TraceDto dto ) {
    return new ResponseEntity<>(traceService.findTraceByPeriode(dto.getDateD(), dto.getDateF()), HttpStatus.OK);
}


@GetMapping("/traces/{id}")
@Operation(summary = "Fetch all produits")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "${swagger.http-status.200}"),
        @ApiResponse(responseCode = "204", description = "${swagger.http-status.204}"),
        @ApiResponse(responseCode = "500", description = "${swagger.http-status.500}")
})
public ResponseEntity<TraceDto> findTraceById(@PathVariable Long id  ) {
    return  ResponseEntity.ok(traceService.findDetail(id));
}



    
}
