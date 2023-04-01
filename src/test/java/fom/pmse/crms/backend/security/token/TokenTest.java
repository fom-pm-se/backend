package fom.pmse.crms.backend.security.token;

import fom.pmse.crms.backend.security.model.CrmUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class TokenTest {
    private Token token;
    CrmUser user;

    @BeforeEach
    void setUp() {
        user = new CrmUser();
        token = new Token();
        token.setId(1L);
        token.setToken("token");
        token.setTokenType(TokenType.BEARER);
        token.setRevoked(false);
        token.setExpired(false);
        token.setUser(user);
    }

    @Test
    void getId() {
        assertEquals(1L, token.getId());
    }

    @Test
    void getToken() {
        assertEquals("token", token.getToken());
    }

    @Test
    void getTokenType() {
        assertEquals(TokenType.BEARER, token.getTokenType());
    }

    @Test
    void isRevoked() {
        assertFalse(token.isRevoked());
    }

    @Test
    void isExpired() {
        assertFalse(token.isExpired());
    }

    @Test
    void getUser() {
        assertEquals(user, token.getUser());
    }
}