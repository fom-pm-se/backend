package fom.pmse.crms.backend.converter;

import fom.pmse.crms.backend.payload.response.CrmUserDto;
import fom.pmse.crms.backend.security.model.CrmUser;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class CrmUserConverter {
    public static CrmUserDto toDto(CrmUser user) {
        return CrmUserDto.builder()
                .username(user.getUsername())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .isEnabled(user.isEnabled())
                .role(user.getRole().toString())
                .build();
    }
}
