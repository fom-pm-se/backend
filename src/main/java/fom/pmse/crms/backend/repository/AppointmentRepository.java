package fom.pmse.crms.backend.repository;

import fom.pmse.crms.backend.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findAllByPartnerId(Long partnerId);
    List<Appointment> findAllByUserId(Long userId);
}
