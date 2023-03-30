package fom.pmse.crms.backend.security.payload.request;

import lombok.Data;

import java.util.Set;

@Data
public class SignUpRequest {
    private String username;
    private String password;
    private String email;
    private Set<String> roles;
}
