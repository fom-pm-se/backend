package fom.pmse.crms.backend.security.token;

import fom.pmse.crms.backend.security.model.CrmUser;
import jakarta.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "json_web_token")
public class Token {

    @Id
    @SequenceGenerator(name = "tokenSeq", sequenceName = "token_id_seq", allocationSize = 1, initialValue = 1000)
    @GeneratedValue(generator = "tokenSeq", strategy = GenerationType.SEQUENCE)
    @Column(name = "token_id")
    private Long id;

    @Column(unique = true)
    private String token;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType = TokenType.BEARER;

    public boolean revoked;

    public boolean expired;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public CrmUser user;
}
