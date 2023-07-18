package fom.pmse.crms.backend.controller;

import fom.pmse.crms.backend.dto.ErrorDto;
import fom.pmse.crms.backend.exception.AppointmentCreationFailedException;
import fom.pmse.crms.backend.exception.BadRequestException;
import fom.pmse.crms.backend.exception.InvalidPasswordException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {BadRequestException.class, UsernameNotFoundException.class, InvalidPasswordException.class, AppointmentCreationFailedException.class})
    protected ResponseEntity<ErrorDto> handleException(Exception ex) {
        return ResponseEntity.badRequest().body(new ErrorDto(ex.getMessage()));
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    protected ResponseEntity<ErrorDto> handleIllegalArgumentException(Exception ex) {
        return ResponseEntity.badRequest().body(new ErrorDto(ex.getMessage()));
    }
}
