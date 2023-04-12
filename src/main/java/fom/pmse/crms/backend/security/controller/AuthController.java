package fom.pmse.crms.backend.security.controller;

import fom.pmse.crms.backend.dto.ErrorDto;
import fom.pmse.crms.backend.security.payload.request.LoginRequest;
import fom.pmse.crms.backend.security.payload.request.SignUpRequest;
import fom.pmse.crms.backend.security.payload.response.AuthenticationResponse;
import fom.pmse.crms.backend.security.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {
    private final AuthenticationService authenticationService;

    @GetMapping("/exists")
    @Operation(summary = "Check if a user exists", description = "Check if a user exists")
    @ApiResponse(responseCode = "200", description = "The user exists", content = @Content(schema = @Schema(implementation = Boolean.class)))
    @ApiResponse(responseCode = "400", description = "The user does not exist", content = @Content(schema = @Schema(implementation = Boolean.class)))
    public ResponseEntity<Boolean> exists(
            @RequestParam String username
    ) {
        if (username == null || username.isBlank()) {
            return ResponseEntity.badRequest().body(false);
        }
        return ResponseEntity.ok(authenticationService.isUsernameAvailable(username));
    }

    @PostMapping("/signup")
    @Operation(summary = "Register a new user in the system", description = "Register a new user in the system. The user will be assigned the role USER. The password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter and one number.")
    @ApiResponse(responseCode = "200", description = "The user was successfully registered", content = @Content(schema = @Schema(implementation = AuthenticationResponse.class)))
    @ApiResponse(responseCode = "400", description = "The user could not be registered. Response contains details", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    public ResponseEntity<?> signUp(
            @RequestBody SignUpRequest signUpRequestDto
    ) {
        try {
            if (!authenticationService.isUsernameAvailable(signUpRequestDto.getUsername())) {
                ErrorDto errorDto = new ErrorDto("Der Benutzername ist bereits vergeben.");
                return ResponseEntity.badRequest().body(errorDto);
            }
            if (!authenticationService.isPasswordValid(signUpRequestDto.getPassword())) {
                ErrorDto errorDto = new ErrorDto("Das Passwort ist nicht gültig. Es muss mindestens 8 Zeichen lang sein und mindestens einen Großbuchstaben, einen Kleinbuchstaben und eine Zahl enthalten.");
                return ResponseEntity.badRequest().body(errorDto);
            }
            AuthenticationResponse response = authenticationService.register(signUpRequestDto);
            log.info("Registration was successful for user {}:", signUpRequestDto.getUsername());
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Registration failed for user {}:", signUpRequestDto.getUsername(), ex);
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping(value = "/signin")
    @Operation(summary = "Authenticate a user", description = "Returns a JWT token if the user is successfully authenticated")
    @ApiResponse(responseCode = "200", description = "The user was successfully authenticated", content = @Content(schema = @Schema(implementation = AuthenticationResponse.class)))
    @ApiResponse(responseCode = "400", description = "The user could not be authenticated. Response contains details", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    public ResponseEntity<?> signIn(
            @RequestBody LoginRequest request
    ) {
        AuthenticationResponse response;
        try {
            response = authenticationService.authenticate(request);
            log.info("Authentication Successful");
            return ResponseEntity.ok(response);
        } catch (LockedException ex) {
            ErrorDto errorDto = new ErrorDto("Der Account ist gesperrt.");
            return ResponseEntity.status(401).body(errorDto);
        } catch (Exception ex) {
            log.error("Authentication failed", ex);
            ErrorDto errorDto = new ErrorDto("Benutzername oder Passwort ist falsch.");
            return ResponseEntity.status(401).body(errorDto);
        }
    }
}
