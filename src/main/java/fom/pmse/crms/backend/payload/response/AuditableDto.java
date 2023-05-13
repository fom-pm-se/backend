package fom.pmse.crms.backend.payload.response;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class AuditableDto {
    private String createdByUsername;
    private String updatedByUsername;
    private String creationTime;
    private String updateTime;
}
