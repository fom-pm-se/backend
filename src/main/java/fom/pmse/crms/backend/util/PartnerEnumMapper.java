package fom.pmse.crms.backend.util;

import fom.pmse.crms.backend.model.PartnerType;

public class PartnerEnumMapper {
    public static PartnerType map(String type) {
        return switch (type) {
            case "Kunde" -> PartnerType.CUSTOMER;
            case "Zulieferer" -> PartnerType.SUPPLIER;
            case "Sonstiger Geschäftspartner" -> PartnerType.OTHER;
            default -> throw new IllegalArgumentException("Unbekannter partner typ: " + type);
        };
    }
}
