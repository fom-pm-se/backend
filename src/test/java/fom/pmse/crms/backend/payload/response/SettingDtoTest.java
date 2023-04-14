package fom.pmse.crms.backend.payload.response;

import fom.pmse.crms.backend.model.Setting;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SettingDtoTest {
    private static Setting setting;
    private static SettingDto settingDto;
    @BeforeAll
    public static void init() {
        setting = new Setting();
        setting.setTechnicalName("test");
        setting.setName("test");
        setting.setDescription("test");
        setting.setActive(true);

        settingDto = new SettingDto();
        settingDto.setTechnicalName("test");
        settingDto.setName("test");
        settingDto.setDescription("test");
        settingDto.setActive(true);
    }

    @Test
    void fromSetting() {
        SettingDto settingDto = SettingDto.fromSetting(setting);
        assertEquals(setting.getTechnicalName(), settingDto.getTechnicalName());
        assertEquals(setting.getName(), settingDto.getName());
        assertEquals(setting.getDescription(), settingDto.getDescription());
        assertEquals(setting.isActive(), settingDto.isActive());
    }

    @Test
    void getTechnicalName() {
        assertEquals("test", settingDto.getTechnicalName());
    }

    @Test
    void getName() {
        assertEquals("test", settingDto.getTechnicalName());
    }

    @Test
    void getDescription() {
        assertEquals("test", settingDto.getTechnicalName());
    }

    @Test
    void isActive() {
        assertTrue(settingDto.isActive());
    }
}