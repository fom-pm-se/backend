package fom.pmse.crms.backend.payload.request;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordDto {
    private String oldPassword;
    private String newPassword;
    private String PasswordRepeat;
}
