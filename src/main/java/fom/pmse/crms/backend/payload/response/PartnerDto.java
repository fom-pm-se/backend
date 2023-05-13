package fom.pmse.crms.backend.payload.response;

import fom.pmse.crms.backend.model.Partner;
import fom.pmse.crms.backend.model.PartnerType;
import fom.pmse.crms.backend.util.CrmTimeHelper;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartnerDto extends AuditableDto {
    private Long id;
    private String address;
    private String name;
    private String phoneNumber;
    private String type;

    public static PartnerDto fromPartner(Partner partner) {
        PartnerDto partnerDto = new PartnerDto();
        partnerDto.setId(partner.getId());
        partnerDto.setAddress(partner.getAddress());
        partnerDto.setName(partner.getName());
        partnerDto.setPhoneNumber(partner.getPhoneNumber());
        partnerDto.setType(partner.getType().name);
        partnerDto.setCreatedByUsername(partner.getCreatedBy());
        partnerDto.setUpdatedByUsername(partner.getUpdatedByUser());
        partnerDto.setCreationTime(CrmTimeHelper.format(partner.getCreationTime()));
        partnerDto.setUpdateTime(CrmTimeHelper.format(partner.getUpdateTime()));
        return partnerDto;
    }
}
