package fom.pmse.crms.backend.payload.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class AuditableDto {
    private String createdByUsername;
    private String updatedByUsername;
    private LocalDateTime creationTime;
    private LocalDateTime updateTime;
}
