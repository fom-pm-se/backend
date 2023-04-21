package fom.pmse.crms.backend.controller;

import fom.pmse.crms.backend.payload.request.ChangeSettingDto;
import fom.pmse.crms.backend.payload.response.SettingDto;
import fom.pmse.crms.backend.payload.response.SettingsShortDto;
import fom.pmse.crms.backend.service.SettingsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "Returns all settings with their technical name, description and current status")
    @ApiResponse(responseCode = "200", description = "List of settings", content = @Content(schema = @Schema(implementation = SettingDto.class)))
    public ResponseEntity<?> getAllSettings() {
        return ResponseEntity.ok(settingsService.getAllSettings());
    }

    @GetMapping("/all")
    @Operation(summary = "Returns all settings with their technical name and current status")
    @ApiResponse(responseCode = "200", description = "List of settings", content = @Content(schema = @Schema(implementation = SettingsShortDto.class)))
    public ResponseEntity<List<SettingsShortDto>> getAllShortSettings() {
        return ResponseEntity.ok(settingsService.getAllSettingsShort());
    }

    @PutMapping("/set/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Sets all settings to the given values")
    @ApiResponse(responseCode = "200", description = "New Values have been saved successfully", content = @Content(schema = @Schema(implementation = SettingDto.class)))
    public ResponseEntity<?> setSettings(@RequestBody ChangeSettingDto[] changeSettingDtos) {
        for (ChangeSettingDto changeSettingDto : changeSettingDtos) {
            settingsService.setSetting(changeSettingDto.getTechnicalName(), changeSettingDto.isActive());
        }
        return ResponseEntity.ok().body(settingsService.getAllSettings());
    }

    @PutMapping("/set")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Sets a single setting to the given value")
    @ApiResponse(responseCode = "200", description = "New Value has been saved successfully", content = @Content(schema = @Schema(implementation = SettingDto.class)))
    public ResponseEntity<List<SettingDto>> setSetting(@RequestBody ChangeSettingDto changeSettingDto) {
        settingsService.setSetting(changeSettingDto.getTechnicalName(), changeSettingDto.isActive());
        return ResponseEntity.ok().body(settingsService.getAllSettings());
    }
}
