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
        settingsService.registerSetting("Benutzerregistrierung", "Erlaubt es Benutzern sich selbst zu registrieren", "all_reg", false);
        settingsService.registerSetting("Benutzerlöschung", "Erlaubt es Benutzern sich selbst zu löschen", "all_del", true);
        settingsService.registerSetting("Benutzeränderung", "Erlaubt es Benutzern ihre Daten zu ändern", "all_usr_chng", true);
        settingsService.registerSetting("Passwort ändern", "Erlaubt es Benutzern ihr Passwort zu ändern", "all_usr_chng_psswd", true);
        settingsService.registerSetting("Automatischer Login nach registrierung", "Benutzer werden nach der Registrierung automatisch eingeloggt", "all_auto_forwd", true);
    }
}
