package fom.pmse.crms.backend.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PartnerTypeTest {

    @Test
    void valueOfKundeReturnsInstanceOfCustomer() {
        assertEquals(PartnerType.CUSTOMER, PartnerType.valueOf("Kunde"));
    }
}