package fom.pmse.crms.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Note extends Auditable {
    @SequenceGenerator(name = "note_sequence_generator", sequenceName = "note_sequence", allocationSize = 1, initialValue = 1000)
    @GeneratedValue(generator = "note_sequence_generator")
    @Id
    private Long id;

    private String title;
    private String content;

    @ManyToOne
    private Partner partner;
}
