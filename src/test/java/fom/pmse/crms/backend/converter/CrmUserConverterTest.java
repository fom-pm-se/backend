package fom.pmse.crms.backend.converter;

import fom.pmse.crms.backend.payload.response.CrmUserDto;
import fom.pmse.crms.backend.security.model.CrmUser;
import fom.pmse.crms.backend.security.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CrmUserConverterTest {
    private CrmUser crmUser;

    @BeforeEach
    public void setup() {
        crmUser = new CrmUser();
        crmUser.setUsername("test");
        crmUser.setPassword("test");
        crmUser.setFirstname("test");
        crmUser.setLastname("test");
        crmUser.setPassword("test");
        crmUser.setRole(Role.USER);
    }

    @Test
    void crmUserConverterTest() {
        CrmUserDto crmUserDto = CrmUserConverter.toDto(crmUser);
        assertEquals(crmUser.getUsername(), crmUserDto.getUsername());
        assertEquals(crmUser.getFirstname(), crmUserDto.getFirstname());
        assertEquals(crmUser.getLastname(), crmUserDto.getLastname());
        assertEquals(crmUser.getRole().toString(), crmUserDto.getRole());
    }
}