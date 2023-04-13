package fom.pmse.crms.backend.payload.response;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CrmUserDto {
    private String username;
    private String firstname;
    private String lastname;
    private boolean isEnabled;
    private String role;
}
