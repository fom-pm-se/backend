package fom.pmse.crms.backend.security.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CrmUserTest {
    CrmUser crmUser;
    LocalDateTime startedAt = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        crmUser = new CrmUser();
        crmUser.setUsername("username");
        crmUser.setPassword("password");
        crmUser.setCreatedAt(startedAt);
        crmUser.setUpdatedAt(startedAt);
    }

    @Test
    void userIsInstanceOfUserDetails() {
        assertInstanceOf(UserDetails.class, crmUser);
    }

    @Test
    void getPasswordReturnsCurrentPassword() {
        assertEquals("password", crmUser.getPassword());
    }

    @Test
    void getUsernameReturnsCurrentUsername() {
        assertEquals("username", crmUser.getUsername());
    }

    @Test
    void isAccountNonExpiredReturnsTrue() {
        assertTrue(crmUser.isAccountNonExpired());
    }

    @Test
    void isAccountNonLockedReturnsTrue() {
        assertTrue(crmUser.isAccountNonLocked());
    }

    @Test
    void isCredentialsNonExpiredReturnsTrue() {
        assertTrue(crmUser.isCredentialsNonExpired());
    }

    @Test
    void isEnabledReturnsTrue() {
        assertTrue(crmUser.isEnabled());
    }
}