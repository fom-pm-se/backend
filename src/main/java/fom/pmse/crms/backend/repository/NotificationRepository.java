package fom.pmse.crms.backend.repository;

import fom.pmse.crms.backend.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Long countAllByUser_UsernameAndReadFalse(String username);
    Optional<List<Notification>> findAllByUser_Username(String username);
    Optional<List<Notification>> findAllByUser_UsernameAndReadFalse(String username);
}
