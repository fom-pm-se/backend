package fom.pmse.crms.backend.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PartnerTypeTest {

    @Test
    void partnerTypeGetTypesReturnsCorrectTypes() {
        assertEquals(3, PartnerType.getTypes().size());
        assertTrue(PartnerType.getTypes().contains("Kunde"));
        assertTrue(PartnerType.getTypes().contains("Zulieferer"));
        assertTrue(PartnerType.getTypes().contains("Sonstiger Geschäftspartner"));
    }
}