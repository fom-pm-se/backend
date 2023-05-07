package fom.pmse.crms.backend.model;

import fom.pmse.crms.backend.payload.request.CreatePartnerDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Partner extends Auditable {

    @Id
    @SequenceGenerator(name = "partner_sequence", sequenceName = "partner_sequence", allocationSize = 1, initialValue = 1000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "partner_sequence")
    private Long id;

    private String name;

    private String address;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private PartnerType type;

    public Partner fromDto(CreatePartnerDto dto) {
        this.name = dto.getName();
        this.address = dto.getAddress();
        this.phoneNumber = dto.getPhoneNumber();
        this.type = PartnerType.valueOf(dto.getType());
        return this;
    }

}
