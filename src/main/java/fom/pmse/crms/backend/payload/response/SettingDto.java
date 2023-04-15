package fom.pmse.crms.backend.payload.response;

import fom.pmse.crms.backend.model.Setting;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SettingDto {
    private String technicalName;
    private String name;
    private String description;
    private boolean active;

    public static SettingDto fromSetting(Setting setting) {
        return SettingDto.builder()
                .technicalName(setting.getTechnicalName())
                .name(setting.getName())
                .description(setting.getDescription())
                .active(setting.isActive())
                .build();
    }
}
