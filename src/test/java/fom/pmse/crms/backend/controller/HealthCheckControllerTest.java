package fom.pmse.crms.backend.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HealthCheckControllerTest {

    HealthCheckController healthCheckController;

    @BeforeEach
    void setup() {
        healthCheckController = new HealthCheckController();
    }

    @Test
    void performHealthCheck() {
        assertEquals("OK", healthCheckController.performHealthCheck().getBody());
    }

    @Test
    void healthCheckReturnsStatusCode200() {
        assertEquals(200, healthCheckController.performHealthCheck().getStatusCode().value());
    }
}