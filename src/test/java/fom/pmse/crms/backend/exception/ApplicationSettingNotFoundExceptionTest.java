package fom.pmse.crms.backend.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationSettingNotFoundExceptionTest {

        @Test
        void applicationSettingNotFoundExceptionConstructorTest() {
            ApplicationSettingNotFoundException exception = new ApplicationSettingNotFoundException("Test");
            assertEquals("Test", exception.getMessage());
        }

}