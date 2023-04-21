package fom.pmse.crms.backend.setup;

import fom.pmse.crms.backend.service.SettingsService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RegisterSettings {
    private final SettingsService settingsService;

    @PostConstruct
    public void registerSettings() {
        settingsService.registerSetting("Benutzerregistrierung", "Erlaubt es Benutzern sich selbst zu registrieren", "all_reg", true);
        settingsService.registerSetting("Benutzereinstellungen", "Erlaubt Einstellungen an ihrem Benutzerkonto vorzunehmen", "all_user_settings", true);
        settingsService.registerSetting("Benutzerlöschung", "Erlaubt es Benutzern sich selbst zu löschen", "all_del", true);
        settingsService.registerSetting("Benutzerliste", "Erlaubt es Benutzern die Benutzerliste einzusehen", "all_user_list", true);
        settingsService.registerSetting("Benutzerliste", "Erlaubt es Benutzern die Benutzerliste einzusehen", "all_user_list", true);
    }
}
