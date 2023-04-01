package fom.pmse.crms.backend.security.controller;

import fom.pmse.crms.backend.security.payload.request.LoginRequest;
import fom.pmse.crms.backend.security.payload.request.SignUpRequest;
import fom.pmse.crms.backend.security.payload.response.AuthenticationResponse;
import fom.pmse.crms.backend.security.payload.response.MessageResponse;
import fom.pmse.crms.backend.security.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {
    private final AuthenticationService authenticationService;
    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> signUp(
            @RequestBody SignUpRequest signUpRequestDto
    ) {
        AuthenticationResponse response = authenticationService.register(signUpRequestDto);
        log.info("Registration was successful for user {}:", signUpRequestDto.getUsername());
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/signin")
    public ResponseEntity<AuthenticationResponse> signIn(
            @RequestBody LoginRequest request
    ) {
        AuthenticationResponse response;
        try {
            response = authenticationService.authenticate(request);
            log.info("Authentication Successful");
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            response = new AuthenticationResponse();
            response.setToken("");
            return ResponseEntity.badRequest().body(response);
        }
    }
}
