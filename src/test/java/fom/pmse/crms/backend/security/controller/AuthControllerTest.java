package fom.pmse.crms.backend.security.controller;

import fom.pmse.crms.backend.dto.ErrorDto;
import fom.pmse.crms.backend.security.model.CrmUser;
import fom.pmse.crms.backend.security.payload.request.LoginRequest;
import fom.pmse.crms.backend.security.payload.request.SignUpRequest;
import fom.pmse.crms.backend.security.repository.UserRepository;
import fom.pmse.crms.backend.security.token.TokenRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthControllerTest {

    private SignUpRequest signUpRequest;
    String url;

    @Value("${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @BeforeEach
    void setUp() {
        signUpRequest = new SignUpRequest();
        signUpRequest.setUsername("username");
        signUpRequest.setPassword("password");
        signUpRequest.setFirstname("firstName");
        signUpRequest.setLastname("lastName");

        url = "http://localhost:" + port + "/api/v1/auth/signup";
    }

    @AfterEach
    void tearDown() {
        tokenRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void invalidPasswordReturnsStatus400() {
        signUpRequest.setPassword("123");
        ResponseEntity<ErrorDto> response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(signUpRequest), ErrorDto.class);
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void signUpRejectedWithMessageIfPasswordToShort() {
        signUpRequest.setPassword("123");
        ResponseEntity<ErrorDto> response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(signUpRequest), ErrorDto.class);
        String message = "Das Passwort ist nicht gültig. Es muss mindestens 8 Zeichen lang sein und mindestens einen Großbuchstaben, einen Kleinbuchstaben und eine Zahl enthalten.";
        assertEquals(message, response.getBody().getErrorMessage());
    }

    @Test
    void signUpRejectedWithMessageIfPasswordContainsNoNumber() {
        signUpRequest.setPassword("password");
        ResponseEntity<ErrorDto> response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(signUpRequest), ErrorDto.class);
        String message = "Das Passwort ist nicht gültig. Es muss mindestens 8 Zeichen lang sein und mindestens einen Großbuchstaben, einen Kleinbuchstaben und eine Zahl enthalten.";
        assertEquals(message, response.getBody().getErrorMessage());
    }

    @Test
    void signUpRejectedWithMessageIfPasswordDoesNotContainUppercase() {
        signUpRequest.setPassword("password1");
        ResponseEntity<ErrorDto> response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(signUpRequest), ErrorDto.class);
        String message = "Das Passwort ist nicht gültig. Es muss mindestens 8 Zeichen lang sein und mindestens einen Großbuchstaben, einen Kleinbuchstaben und eine Zahl enthalten.";
        assertEquals(message, response.getBody().getErrorMessage());
    }

    @Test
    void signUpRejectedWithMessageIfPasswordDoesNotContainLowercase() {
        signUpRequest.setPassword("PASSWORD1");
        ResponseEntity<ErrorDto> response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(signUpRequest), ErrorDto.class);
        String message = "Das Passwort ist nicht gültig. Es muss mindestens 8 Zeichen lang sein und mindestens einen Großbuchstaben, einen Kleinbuchstaben und eine Zahl enthalten.";
        assertEquals(message, response.getBody().getErrorMessage());
    }

    @Test
    void signUpRejectedWithMessageIfUserExists() {
        CrmUser user = new CrmUser();
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(signUpRequest.getPassword());
        user.setFirstname(signUpRequest.getFirstname());
        user.setLastname(signUpRequest.getLastname());
        userRepository.save(user);
        ResponseEntity<ErrorDto> response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(signUpRequest), ErrorDto.class);
        String message = "Der Benutzername ist bereits vergeben.";
        assertEquals(message, response.getBody().getErrorMessage());
    }

    @Test
    void signUpAcceptedIfCriteriaMatch() {
        signUpRequest.setPassword("Password1!");
        ResponseEntity<ErrorDto> response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(signUpRequest), ErrorDto.class);
        assertEquals(200, response.getStatusCodeValue());
        CrmUser user = userRepository.findByUsername(signUpRequest.getUsername()).orElse(null);
        assertNotNull(user);
    }
}