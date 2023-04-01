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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JsonWebTokenService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(SignUpRequest signUpRequest) {
        CrmUser user = CrmUser.builder()
                .firstname(signUpRequest.getFirstname())
                .lastname(signUpRequest.getLastname())
                .username(signUpRequest.getUsername())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .role(Role.USER)
                .build();
        CrmUser created = userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        saveUserToken(created, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
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

    private void revokeAllUserTokens(CrmUser crmUser) {
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
}
