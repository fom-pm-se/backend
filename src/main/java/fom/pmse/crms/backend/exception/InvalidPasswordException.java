package fom.pmse.crms.backend.exception;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
        super("Das Passwort muss mindestens 8 Zeichen lang sein und mindestens eine Zahl, einen Großbuchstaben und einen Kleinbuchstaben enthalten.");
    }
}
