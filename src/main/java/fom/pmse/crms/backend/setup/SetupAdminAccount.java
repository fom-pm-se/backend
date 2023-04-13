package fom.pmse.crms.backend.setup;

import fom.pmse.crms.backend.security.model.CrmUser;
import fom.pmse.crms.backend.security.model.Role;
import fom.pmse.crms.backend.security.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class SetupAdminAccount {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Value("${backend.admin.username}")
    private String adminUsername;

    @Value("${backend.admin.password}")
    private String adminPassword;

    @PostConstruct
    public void setupAdminAccount() {
        log.info("Setup admin account");
        LocalDateTime created = LocalDateTime.now();
        Optional<CrmUser> optionalCrmUser = userRepository.findByUsername(adminUsername);
        if (optionalCrmUser.isEmpty()) {
            CrmUser admin = new CrmUser();
            admin.setFirstname("Technical");
            admin.setLastname("Admin");
            admin.setUsername(adminUsername);
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setRole(Role.ADMIN);
            admin.setEnabled(true);
            admin.setCreatedAt(created);
            admin.setUpdatedAt(created);
            log.info("Admin Account '{}' was created at '{}'", adminUsername, created.format(DateTimeFormatter.ISO_ORDINAL_DATE));
            userRepository.save(admin);
        } else {
            log.info("Admin account '{}' already exists, skipping", adminUsername);
        }
    }
}
