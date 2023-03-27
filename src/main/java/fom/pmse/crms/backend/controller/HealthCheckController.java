package fom.pmse.crms.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/api/v1/health")
public class HealthCheckController {
    @GetMapping()
    public ResponseEntity<String> performHealthCheck() {
        return ResponseEntity.ok("OK");
    }
}
