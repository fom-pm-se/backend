package fom.pmse.crms.backend.security.payload.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
