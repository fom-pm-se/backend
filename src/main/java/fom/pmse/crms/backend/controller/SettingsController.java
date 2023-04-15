package fom.pmse.crms.backend.controller;

import fom.pmse.crms.backend.payload.request.ChangeSettingDto;
import fom.pmse.crms.backend.payload.response.SettingsShortDto;
import fom.pmse.crms.backend.service.SettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/v1/settings")
@RequiredArgsConstructor
public class SettingsController {
    private final SettingsService settingsService;

    @GetMapping("/all/long")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getAllSettings() {
        return ResponseEntity.ok(settingsService.getAllSettings());
    }

    @GetMapping("/all")
    public ResponseEntity<List<SettingsShortDto>> getAllShortSettings() {
        return ResponseEntity.ok(settingsService.getAllSettingsShort());
    }

    @PutMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> setSetting(@RequestBody ChangeSettingDto[] changeSettingDtos) {
        for (ChangeSettingDto changeSettingDto : changeSettingDtos) {
            settingsService.setSetting(changeSettingDto.getTechnicalName(), changeSettingDto.isActive());
        }
        return ResponseEntity.ok().body(settingsService.getAllSettings());
    }
}
