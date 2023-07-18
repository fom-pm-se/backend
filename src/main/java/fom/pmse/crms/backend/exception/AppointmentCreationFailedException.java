package fom.pmse.crms.backend.exception;

public class AppointmentCreationFailedException extends RuntimeException {
    public AppointmentCreationFailedException(String message) {
        super(message);
    }
}
