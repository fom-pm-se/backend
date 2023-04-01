package fom.pmse.crms.backend.security.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse {
    private String username;
    private String firstname;
    private String lastname;
    private Set<String> roles;
}
