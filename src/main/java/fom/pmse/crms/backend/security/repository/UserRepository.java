package fom.pmse.crms.backend.security.repository;

import fom.pmse.crms.backend.security.model.CrmUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<CrmUser, Long> {
    Optional<CrmUser> findByUsername(String username);
}
