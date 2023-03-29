package fom.pmse.crms.backend.security.repository;

import fom.pmse.crms.backend.security.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {
    User user;
    @Autowired
    UserRepository userRepository;
    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("username");
        user.setPassword("password");
        userRepository.save(user);
    }

    @Test
    void findByUsernameReturnsUser() {
        Optional<User> optionalUser = userRepository.findByUsername("username");
        assertTrue(optionalUser.isPresent());
    }

    @Test
    void findByUsernameReturnsEmpty() {
        Optional<User> optionalUser = userRepository.findByUsername("username2");
        assertFalse(optionalUser.isPresent());
    }

    @Test
    void findByUsernameReturnsCorrectUser() {
        Optional<User> optionalUser = userRepository.findByUsername("username");
        assertTrue(optionalUser.isPresent());
        assertEquals("username", optionalUser.get().getUsername());
    }
}