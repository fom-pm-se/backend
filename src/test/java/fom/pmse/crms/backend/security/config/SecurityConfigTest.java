package fom.pmse.crms.backend.security.config;

import org.junit.jupiter.api.Test;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import static org.junit.jupiter.api.Assertions.*;

class SecurityConfigTest {

    SecurityConfig securityConfig = new SecurityConfig();
    @Test
    void passwordEncoder() {
        assertInstanceOf(BCryptPasswordEncoder.class, securityConfig.passwordEncoder());
    }
}