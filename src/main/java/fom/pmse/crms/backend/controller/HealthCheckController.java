package fom.pmse.crms.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/health")
public class HealthCheckController {
    @GetMapping
    @Operation(summary = "Perform a health check of the backend")
    @ApiResponse(responseCode = "200", description = "The backend is healthy")
    @ApiResponse(responseCode = "500", description = "The backend is not healthy")
    public ResponseEntity<String> performHealthCheck() {
        return ResponseEntity.ok("OK");
    }
}
