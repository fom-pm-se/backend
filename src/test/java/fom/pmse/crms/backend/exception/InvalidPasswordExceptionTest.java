package fom.pmse.crms.backend.exception;

import static org.junit.jupiter.api.Assertions.*;

class InvalidPasswordExceptionTest {

    @org.junit.jupiter.api.Test
    void invalidPasswordExceptionConstructorTest() {
        InvalidPasswordException exception = new InvalidPasswordException();
        assertEquals("Das Passwort muss mindestens 8 Zeichen lang sein und mindestens eine Zahl, einen Großbuchstaben und einen Kleinbuchstaben enthalten.", exception.getMessage());
    }

}