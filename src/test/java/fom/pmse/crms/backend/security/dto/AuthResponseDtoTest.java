package fom.pmse.crms.backend.security.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AuthResponseDtoTest {
    private AuthResponseDto objectUnderTest;
    private final String validationDate = LocalDateTime.now().toString();

    @BeforeEach
    void setUp() {
        objectUnderTest = new AuthResponseDto();
        objectUnderTest.setUsername("username");
        objectUnderTest.setData(validationDate);
    }

    @Test
    void getUsername() {
        assertEquals("username", objectUnderTest.getUsername());
    }

    @Test
    void getData() {
        assertEquals(validationDate, objectUnderTest.getData());
    }
}