package fom.pmse.crms.backend.security.token;

import fom.pmse.crms.backend.security.model.CrmUser;
import fom.pmse.crms.backend.security.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TokenRepositoryTest {
    Token token;
    CrmUser user;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    UserRepository crmUserRepository;

    final LocalDateTime startTime = LocalDateTime.now();
    @BeforeEach
    void setUp() {
        user = new CrmUser();
        user.setUsername("username");
        user.setPassword("password");
        user.setFirstname("firstName");
        user.setLastname("lastName");
        crmUserRepository.save(user);

        token = new Token();
        token.setToken("token");
        token.setTokenType(TokenType.BEARER);
        token.setRevoked(false);
        token.setExpired(false);
        token.setUser(user);
        tokenRepository.save(token);
    }

    @Test
    void findAllValidTokenByUser() {
        assertEquals(1, tokenRepository.findAllValidTokenByUser(user.getId()).size());
    }

    @Test
    void findByToken() {
        assertEquals(token, tokenRepository.findByToken("token").get());
    }
}