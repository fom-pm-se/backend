package fom.pmse.crms.backend.exception;

public class ApplicationSettingNotFoundException extends RuntimeException {
    public ApplicationSettingNotFoundException(String message) {
        super(message);
    }
}
