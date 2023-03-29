package fom.pmse.crms.backend.security.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    User user;
    LocalDateTime startedAt = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setCreatedAt(startedAt);
        user.setUpdatedAt(startedAt);
    }

    @Test
    void userIsInstanceOfUserDetails() {
        assertInstanceOf(UserDetails.class, user);
    }

    @Test
    void getPasswordReturnsCurrentPassword() {
        assertEquals("password", user.getPassword());
    }

    @Test
    void getUsernameReturnsCurrentUsername() {
        assertEquals("username", user.getUsername());
    }

    @Test
    void isAccountNonExpiredReturnsTrue() {
        assertTrue(user.isAccountNonExpired());
    }

    @Test
    void isAccountNonLockedReturnsTrue() {
        assertTrue(user.isAccountNonLocked());
    }

    @Test
    void isCredentialsNonExpiredReturnsTrue() {
        assertTrue(user.isCredentialsNonExpired());
    }

    @Test
    void isEnabledReturnsTrue() {
        assertTrue(user.isEnabled());
    }
}