package fom.pmse.crms.backend.util;

import fom.pmse.crms.backend.model.PartnerType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PartnerEnumMapperTest {
    @Test
    public void testMap() {
        assertEquals(PartnerEnumMapper.map("Kunde"), PartnerType.CUSTOMER);
        assertEquals(PartnerEnumMapper.map("Zulieferer"), PartnerType.SUPPLIER);
        assertEquals(PartnerEnumMapper.map("Sonstiger Geschäftspartner"), PartnerType.OTHER);
        assertThrows(IllegalArgumentException.class, () -> PartnerEnumMapper.map("Unbekannter Typ"));
    }
}