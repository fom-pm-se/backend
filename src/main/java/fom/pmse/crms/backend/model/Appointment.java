package fom.pmse.crms.backend.model;

import fom.pmse.crms.backend.security.model.CrmUser;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Appointment extends Auditable {

    @Id
    @SequenceGenerator(name = "appointment_generator", sequenceName = "appointment_sequence", allocationSize = 1, initialValue = 1000)
    private Long id;
    private String title;
    private String description;
    private String location;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    @ManyToOne
    private Partner partner;
    @ManyToOne
    private CrmUser user;
}
