package fom.pmse.crms.backend.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BadRequestExceptionTest {

        @Test
        void badRequestExceptionConstructorTest() {
            BadRequestException exception = new BadRequestException("Test");
            assertEquals("Test", exception.getMessage());
        }
}