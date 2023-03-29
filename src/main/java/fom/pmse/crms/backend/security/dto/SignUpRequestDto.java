package fom.pmse.crms.backend.security.dto;

import lombok.Data;

@Data
public class SignUpRequestDto {
    private String username;
    private String password;
}
