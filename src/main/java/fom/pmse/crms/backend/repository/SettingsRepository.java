package fom.pmse.crms.backend.repository;

import fom.pmse.crms.backend.model.Setting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SettingsRepository extends JpaRepository<Setting, Long> {
    Optional<Setting> findByName(String name);
    Optional<Setting> findByTechnicalName(String technicalName);
}
