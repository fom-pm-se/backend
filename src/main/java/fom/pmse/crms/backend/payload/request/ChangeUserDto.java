package fom.pmse.crms.backend.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangeUserDto {
    @NotNull private String firstname;
    @NotNull private String lastname;
    @NotNull private String username;
}
