package fom.pmse.crms.backend.security.controller;

import fom.pmse.crms.backend.security.dto.AuthResponseDto;
import fom.pmse.crms.backend.security.dto.SignUpRequestDto;
import fom.pmse.crms.backend.security.model.User;
import fom.pmse.crms.backend.security.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.media.Content;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @PostMapping("/signup")
    @Operation(summary = "Sign up a new user and performs serverside validation")
    @ApiResponse(responseCode = "200", description = "User created successfully", content = @Content(schema = @Schema(implementation = AuthResponseDto.class)))
    @ApiResponse(responseCode = "400", description = "Username already exists or password or username is too short. Message contains details", content = @Content(schema = @Schema(implementation = String.class, description = "Message contains details")))
    public ResponseEntity<?> signUp(@Parameter @RequestBody SignUpRequestDto signUpRequestDto) {
        log.info("Received request to sign up user: {}", signUpRequestDto.getUsername());
        if (signUpRequestDto.getPassword().length() < 8) {
            log.info("Password is too short {}, username {}", signUpRequestDto.getPassword().length(), signUpRequestDto.getUsername());
            return ResponseEntity.badRequest().body("Password must be at least 8 characters long");
        }

        if (signUpRequestDto.getUsername().length() < 3) {
            log.info("Username is too short {}, username {}", signUpRequestDto.getUsername().length(), signUpRequestDto.getUsername());
            return ResponseEntity.badRequest().body("Username must be at least 3 characters long");
        }

        if (userRepository.findByUsername(signUpRequestDto.getUsername()).isPresent()) {
            log.info("Username already exists {}", signUpRequestDto.getUsername());
            return ResponseEntity.badRequest().body("Username already exists");
        }
        log.info("Creating user: {}", signUpRequestDto.getUsername());
        User user = new User();
        user.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));
        log.info("Password encoded for user: {}", signUpRequestDto.getUsername());
        log.info("Encoded password is {}", user.getPassword());
        user.setUsername(signUpRequestDto.getUsername());
        user.setCreatedAt(LocalDateTime.now());
        log.info("Saving user: {} at time {}", signUpRequestDto.getUsername(), user.getCreatedAt());
        userRepository.save(user);

        log.info("User saved: {}", signUpRequestDto.getUsername());
        AuthResponseDto authResponseDto = new AuthResponseDto();
        authResponseDto.setUsername(user.getUsername());
        authResponseDto.setData(user.getCreatedAt().toString());
        log.info("User Created. Returning response for user: {}", signUpRequestDto.getUsername());
        return ResponseEntity.ok().body(authResponseDto);
    }
}
