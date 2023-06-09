package fom.pmse.crms.backend.service;

import fom.pmse.crms.backend.exception.BadRequestException;
import fom.pmse.crms.backend.model.Setting;
import fom.pmse.crms.backend.payload.response.SettingDto;
import fom.pmse.crms.backend.payload.response.SettingsShortDto;
import fom.pmse.crms.backend.repository.SettingsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SettingsService {
    private final SettingsRepository settingsRepository;

    public Boolean isSettingEnabled(String technicalName) {
        Setting setting = settingsRepository.findByTechnicalName(technicalName).orElseThrow(() -> new BadRequestException("Die Einstellung mit dem technischen Namen " + technicalName + " wurde nicht gefunden"));
        log.info("Request for Setting {} -> {}", technicalName, setting.isActive());
        return setting.isActive();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public Boolean setSetting(String technicalName, boolean active) {
        Setting setting = settingsRepository.findByTechnicalName(technicalName).orElseThrow(() -> new BadRequestException("Die Einstellung mit dem technischen Namen " + technicalName + " wurde nicht gefunden"));
        setting.setActive(active);
        settingsRepository.save(setting);
        log.info("Setting {} is now {}", technicalName, setting.isActive());
        return setting.isActive();
    }

    public List<SettingDto> getAllSettings() {
        return settingsRepository.findAll().stream().map(SettingDto::fromSetting).toList();
    }

    public List<SettingsShortDto> getAllSettingsShort() {
        return settingsRepository.findAll().stream().map(SettingsShortDto::fromSetting).toList();
    }

    public void registerSetting(String name, String description, String technicalName, boolean active) {
        log.info("Registering setting '{}' with technical name {} --> {}", name, technicalName, active);
        if (settingsRepository.findByName(name).isEmpty()) {
            Setting setting = new Setting();
            setting.setName(name);
            setting.setTechnicalName(technicalName);
            setting.setDescription(description);
            setting.setActive(active);
            settingsRepository.save(setting);
        }
    }
}
