package fom.pmse.crms.backend.payload.response;

import fom.pmse.crms.backend.model.Partner;
import fom.pmse.crms.backend.model.PartnerType;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartnerDto {
    private Long id;
    private String address;
    private String name;
    private String phoneNumber;
    private PartnerType type;
    private String createdByUsername;
    private String updatedByUsername;
    private LocalDateTime creationTime;
    private LocalDateTime updateTime;

    public static PartnerDto fromPartner(Partner partner) {
        PartnerDto partnerDto = new PartnerDto();
        partnerDto.setId(partner.getId());
        partnerDto.setAddress(partner.getAddress());
        partnerDto.setName(partner.getName());
        partnerDto.setPhoneNumber(partner.getPhoneNumber());
        partnerDto.setType(partner.getType());
        partnerDto.setCreatedByUsername(partner.getCreatedBy());
        partnerDto.setUpdatedByUsername(partner.getUpdatedByUser());
        partnerDto.setCreationTime(partner.getCreationTime());
        partnerDto.setUpdateTime(partner.getUpdateTime());
        return partnerDto;
    }
}
