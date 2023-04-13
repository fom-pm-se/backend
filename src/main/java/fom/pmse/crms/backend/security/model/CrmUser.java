package fom.pmse.crms.backend.security.model;

import fom.pmse.crms.backend.security.token.Token;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Table(name = "users")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CrmUser implements UserDetails {

    @GeneratedValue(generator = "users_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "users_id_seq", sequenceName = "users_id_seq", allocationSize = 1, initialValue = 1000)
    @Id
    @Column(name = "user_id")
    private Long id;

    private String firstname;

    private String lastname;

    @Column(unique = true)
    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private boolean isEnabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { return isEnabled; }

    @Override
    public boolean isCredentialsNonExpired() {
        return updatedAt.isAfter(LocalDateTime.now().minusMonths(1));
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }
}
