package fom.pmse.crms.backend.payload.request;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangeSettingDto {
    private String technicalName;
    private boolean active;
}
