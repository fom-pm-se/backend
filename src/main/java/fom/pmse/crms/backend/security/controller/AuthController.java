package fom.pmse.crms.backend.security.controller;

import fom.pmse.crms.backend.security.config.jwt.JwtTokenProvider;
import fom.pmse.crms.backend.security.model.CrmUser;
import fom.pmse.crms.backend.security.payload.request.LoginRequest;
import fom.pmse.crms.backend.security.payload.request.SignUpRequest;
import fom.pmse.crms.backend.security.payload.response.MessageResponse;
import fom.pmse.crms.backend.security.repository.UserRepository;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    @PostMapping("/signup")
    @Operation(summary = "Sign up a new user and performs serverside validation")
    @ApiResponse(responseCode = "200", description = "User created successfully", content = @Content(schema = @Schema(implementation = MessageResponse.class)))
    @ApiResponse(responseCode = "400", description = "Username already exists or password or username is too short. Message contains details", content = @Content(schema = @Schema(implementation = String.class, description = "Message contains details")))
    public ResponseEntity<?> signUp(@Parameter @RequestBody SignUpRequest signUpRequestDto) {
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
        CrmUser crmUser = new CrmUser();
        crmUser.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));
        log.info("Password encoded for user: {}", signUpRequestDto.getUsername());
        log.info("Encoded password is {}", crmUser.getPassword());
        crmUser.setUsername(signUpRequestDto.getUsername());
        LocalDateTime creationTime = LocalDateTime.now();
        crmUser.setCreatedAt(creationTime);
        crmUser.setUpdatedAt(creationTime);
        log.info("Saving user: {} at time {}", signUpRequestDto.getUsername(), crmUser.getCreatedAt());
        userRepository.save(crmUser);

        log.info("User saved: {}", signUpRequestDto.getUsername());
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("User created successfully");
        log.info("User Created. Returning response for user: {}", signUpRequestDto.getUsername());
        return ResponseEntity.ok().body(messageResponse);
    }

    @PostMapping(value = "/signin", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Sign in a user and returns a JWT token")
    @ApiResponse(responseCode = "200", description = "User signed in successfully", content = @Content(schema = @Schema(implementation = MessageResponse.class)))
    @ApiResponse(responseCode = "401", description = "User is not authorized to access this resource", content = @Content(schema = @Schema(implementation = MessageResponse.class, description = "Message contains details")))
    @ApiResponse(responseCode = "400", description = "Username or password is null", content = @Content(schema = @Schema(implementation = MessageResponse.class, description = "Message contains details")))
    public ResponseEntity<?> signIn(@RequestBody LoginRequest request) {
        log.info("Received request to sign in user: {}", request.getUsername());
        MessageResponse messageResponse = new MessageResponse();
        if (request.getUsername() == null || request.getPassword() == null) {
            log.info("Username or password is null");
            messageResponse.setMessage("Username or password is null");
            return ResponseEntity.badRequest().body(messageResponse);
        }
        if (userRepository.findByUsername(request.getUsername()).isEmpty()) {
            log.info("User not found: {}", request.getUsername());
            messageResponse.setMessage("User not found");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(messageResponse);
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        return ResponseEntity.ok(jwtTokenProvider.generateToken(authentication));
    }
}
