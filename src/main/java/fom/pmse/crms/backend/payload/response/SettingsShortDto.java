package fom.pmse.crms.backend.payload.response;

import fom.pmse.crms.backend.model.Setting;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SettingsShortDto {
    private String technicalName;
    private boolean active;
    public static SettingsShortDto fromSetting(Setting setting) {
        return SettingsShortDto.builder()
                .technicalName(setting.getTechnicalName())
                .active(setting.isActive())
                .build();
    }
}
