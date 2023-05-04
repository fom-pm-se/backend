package fom.pmse.crms.backend.payload.request;

import fom.pmse.crms.backend.model.Partner;
import fom.pmse.crms.backend.model.PartnerType;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePartnerDto {
    private String address;
    private String name;
    private String phoneNumber;
    private PartnerType type;

    public Partner toPartner() {
        return Partner.builder()
                .address(address)
                .name(name)
                .phoneNumber(phoneNumber)
                .type(type)
                .build();
    }
}
