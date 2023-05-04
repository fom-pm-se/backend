package fom.pmse.crms.backend.model;

import java.util.Arrays;
import java.util.List;

public enum PartnerType {
    CUSTOMER("Kunde"),
    SUPPLIER("Zulieferer"),
    OTHER("Sonstiger Geschäftspartner");

    public final String name;

    PartnerType(String name) {
        this.name = name;
    }

    public static List<String> getTypes() {
        return Arrays.asList(CUSTOMER.name, SUPPLIER.name, OTHER.name);
    }
}
