package fom.pmse.crms.backend.payload.request;

import fom.pmse.crms.backend.model.Partner;
import fom.pmse.crms.backend.model.PartnerType;
import fom.pmse.crms.backend.util.PartnerEnumMapper;
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
    private String type;

    public Partner toPartner() {
        return Partner.builder()
                .address(address)
                .name(name)
                .phoneNumber(phoneNumber)
                .type(PartnerEnumMapper.map(type))
                .build();
    }
}
