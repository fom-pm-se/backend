package fom.pmse.crms.backend.controller;

import fom.pmse.crms.backend.payload.request.CreatePartnerDto;
import fom.pmse.crms.backend.payload.response.PartnerDto;
import fom.pmse.crms.backend.service.PartnerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/api/v1/partner")
@RequiredArgsConstructor
public class PartnerController {
    private final PartnerService partnerService;

    @GetMapping
    @Operation(summary = "Returns list of all partners")
    @ApiResponse(responseCode = "200", description = "List of partners")
    @ApiResponse(responseCode = "400", description = "Username is empty or user not found")
    public ResponseEntity<List<PartnerDto>> getAllPartner() {
        return ResponseEntity.ok(partnerService.getAllPartners());
    }

    @PostMapping
    @Operation(summary = "Creates a new partner")
    @ApiResponse(responseCode = "200", description = "Partner created")
    @ApiResponse(responseCode = "400", description = "Username is empty or user not found")
    public ResponseEntity<PartnerDto> createPartner(@RequestBody CreatePartnerDto partnerDto) {
        return ResponseEntity.ok(partnerService.createPartner(partnerDto));
    }
}
