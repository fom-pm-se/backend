package fom.pmse.crms.backend.repository;

import fom.pmse.crms.backend.model.Partner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PartnerRepository extends JpaRepository<Partner, Long> {
    Optional<Partner> findByName(String name);
}
