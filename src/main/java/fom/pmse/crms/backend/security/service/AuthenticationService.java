package fom.pmse.crms.backend.security.service;

import fom.pmse.crms.backend.security.model.CrmUser;
import fom.pmse.crms.backend.security.model.Role;
import fom.pmse.crms.backend.security.payload.request.LoginRequest;
import fom.pmse.crms.backend.security.payload.request.SignUpRequest;
import fom.pmse.crms.backend.security.payload.response.AuthenticationResponse;
import fom.pmse.crms.backend.security.repository.UserRepository;
import fom.pmse.crms.backend.security.token.Token;
import fom.pmse.crms.backend.security.token.TokenRepository;
import fom.pmse.crms.backend.security.token.TokenType;
import fom.pmse.crms.backend.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JsonWebTokenService jwtService;
    private final AuthenticationManager authenticationManager;
    private final NotificationService notificationService;

    public AuthenticationResponse register(SignUpRequest signUpRequest) {
        LocalDateTime creationTimeStamp = LocalDateTime.now();
        CrmUser user = CrmUser.builder()
                .firstname(signUpRequest.getFirstname())
                .lastname(signUpRequest.getLastname())
                .username(signUpRequest.getUsername())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .isEnabled(true)
                .createdAt(creationTimeStamp)
                .updatedAt(creationTimeStamp)
                .role(Role.USER)
                .build();
        CrmUser created = userRepository.save(user);
        notificationService.createNotification(created.getUsername(), "Willkommen bei der PMSE-CRMS", "Dein Benutzer wurde erfolgreich registriert. Schau dich gerne um!", "/settings");
        String jwtToken = jwtService.generateToken(user);
        saveUserToken(created, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public boolean isUsernameAvailable(String username) {
        return userRepository.findByUsername(username).isEmpty();
    }

    public boolean isPasswordValid(String password) {
        return password.length() >= 8
                && password.matches(".*[A-Z].*")
                && password.matches(".*[a-z].*")
                && password.matches(".*[0-9].*");
    }

    public AuthenticationResponse authenticate(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        var user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow();
        var token = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, token);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    private void saveUserToken(CrmUser user, String jwt) {
        log.info("Saving Token for user {}", user.getUsername());
        Token token = Token.builder()
                .user(user)
                .token(jwt)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    public void revokeAllUserTokens(CrmUser crmUser) {
        log.info("Revoking Tokens for user: {}", crmUser.getUsername());
        var validUserTokens = tokenRepository.findAllValidTokenByUser(crmUser.getId());
        if (validUserTokens.isEmpty()) return;
        validUserTokens.forEach(
                token -> {
                    token.setExpired(true);
                    token.setRevoked(true);
                }
        );
        tokenRepository.saveAll(validUserTokens);
    }

    public void logout(String username) {
        userRepository.findByUsername(username).ifPresent(this::revokeAllUserTokens);
    }
}
