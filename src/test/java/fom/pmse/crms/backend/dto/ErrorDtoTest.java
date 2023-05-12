package fom.pmse.crms.backend.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ErrorDtoTest {

        @Test
        void errorDtoConstructorTest() {
            ErrorDto errorDto = new ErrorDto("Test");
            assertEquals("Test", errorDto.getErrorMessage());
        }

        @Test
        void errorDtoWithNoArgsConstructorTest() {
            ErrorDto errorDto = new ErrorDto();
            errorDto.setErrorMessage("Test");
            assertEquals("Test", errorDto.getErrorMessage());
        }
}