package fom.pmse.crms.backend.security.repository;

import fom.pmse.crms.backend.security.model.CrmUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CrmUserRepositoryTest {
    CrmUser crmUser;
    @Autowired
    UserRepository userRepository;
    @BeforeEach
    void setUp() {
        crmUser = new CrmUser();
        crmUser.setUsername("username");
        crmUser.setPassword("password");
        userRepository.save(crmUser);
    }

    @Test
    void findByUsernameReturnsUser() {
        Optional<CrmUser> optionalUser = userRepository.findByUsername("username");
        assertTrue(optionalUser.isPresent());
    }

    @Test
    void findByUsernameReturnsEmpty() {
        Optional<CrmUser> optionalUser = userRepository.findByUsername("username2");
        assertFalse(optionalUser.isPresent());
    }

    @Test
    void findByUsernameReturnsCorrectUser() {
        Optional<CrmUser> optionalUser = userRepository.findByUsername("username");
        assertTrue(optionalUser.isPresent());
        assertEquals("username", optionalUser.get().getUsername());
    }
}