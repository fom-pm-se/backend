package fom.pmse.crms.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Setting {
    @SequenceGenerator(name = "setting_generator", sequenceName = "setting_sequence", initialValue = 1000)
    @GeneratedValue(generator = "setting_generator")
    @Id
    private Long id;
    private String technicalName;
    private String name;
    private String description;
    private boolean active;
}
