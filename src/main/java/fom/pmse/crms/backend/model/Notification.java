package fom.pmse.crms.backend.model;

import fom.pmse.crms.backend.security.model.CrmUser;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    @SequenceGenerator(name = "notification_id_seq", sequenceName = "notification_id_seq", allocationSize = 1, initialValue = 1000)
    @GeneratedValue(generator = "notification_id_seq")
    @Id
    private long id;

    private String title;
    private String subtitle;
    private LocalDateTime timestamp;
    private String url;

    @ManyToOne
    @Cascade({CascadeType.ALL})
    private CrmUser user;

    private boolean read;
}
