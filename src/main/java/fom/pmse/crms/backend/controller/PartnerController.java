package fom.pmse.crms.backend.controller;

import fom.pmse.crms.backend.payload.request.CreatePartnerDto;
import fom.pmse.crms.backend.payload.response.PartnerDto;
import fom.pmse.crms.backend.service.PartnerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/api/v1/partner")
@RequiredArgsConstructor
@Slf4j
public class PartnerController {
    private final PartnerService partnerService;

    @GetMapping
    @Operation(summary = "Returns list of all partners")
    @ApiResponse(responseCode = "200", description = "List of partners")
    @ApiResponse(responseCode = "400", description = "Username is empty or user not found")
    public ResponseEntity<List<PartnerDto>> getAllPartner() {
        log.info("Request to get all partners received");
        return ResponseEntity.ok(partnerService.getAllPartners());
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Returns partner with given id")
    @ApiResponse(responseCode = "200", description = "Partner with given id")
    @ApiResponse(responseCode = "400", description = "Id is empty or user not found")
    public ResponseEntity<PartnerDto> getPartnerById(@PathVariable Long id) {
        return ResponseEntity.ok(partnerService.getPartnerById(id));
    }

    @PostMapping
    @Operation(summary = "Creates a new partner")
    @ApiResponse(responseCode = "200", description = "Partner created")
    @ApiResponse(responseCode = "400", description = "Username is empty or user not found")
    public ResponseEntity<PartnerDto> createPartner(@RequestBody CreatePartnerDto partnerDto) {
        return ResponseEntity.ok(partnerService.createPartner(partnerDto));
    }

    @GetMapping(value = "/types")
    public ResponseEntity<List<String>> getAllPartnerTypes() {
        return ResponseEntity.ok(partnerService.getAllPartnerTypes());
    }
}
