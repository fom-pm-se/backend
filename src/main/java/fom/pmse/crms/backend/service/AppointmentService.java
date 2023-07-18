package fom.pmse.crms.backend.service;

import fom.pmse.crms.backend.exception.AppointmentCreationFailedException;
import fom.pmse.crms.backend.model.Appointment;
import fom.pmse.crms.backend.model.Partner;
import fom.pmse.crms.backend.payload.request.AppointmentDto;
import fom.pmse.crms.backend.payload.response.PartnerDto;
import fom.pmse.crms.backend.repository.AppointmentRepository;
import fom.pmse.crms.backend.security.model.CrmUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final UserService userService;
    private final PartnerService partnerService;
    private final NotificationService notificationService;

    public AppointmentDto createAppointment(AppointmentDto appointmentDto) {
        log.info("Creating appointment for user {} and partner {}", appointmentDto.getUserId(), appointmentDto.getPartnerId());
        CrmUser user = userService.getUserById(appointmentDto.getUserId());
        PartnerDto partner = partnerService.getPartnerById(appointmentDto.getPartnerId());
        if (user == null || partner == null) {
            log.error("User or partner not found");
            throw new AppointmentCreationFailedException("User or partner not found");
        }
        Appointment appointment = Appointment.builder()
                .title(appointmentDto.getTitle())
                .description(appointmentDto.getDescription())
                .location(appointmentDto.getLocation())
                .startAt(appointmentDto.getStartAt())
                .endAt(appointmentDto.getEndAt())
                .user(user)
                .partner(Partner.builder()
                        .id(partner.getId())
                        .build())
                .build();

        appointment = appointmentRepository.save(appointment);
        notificationService.createNotification(user.getUsername(), "Termin erstellt", "Termin mit " + partner.getName() + " erstellt", "/appointments/" + appointment.getId());
        return AppointmentDto.fromAppointment(appointment);
    }

    public Appointment deleteAppointment(Long appointmentId) {
        log.info("Deleting appointment with id {}", appointmentId);
        Appointment appointment = appointmentRepository.findById(appointmentId).orElse(null);
        if (appointment == null) {
            log.error("Appointment not found");
            throw new AppointmentCreationFailedException("Appointment not found");
        }
        notificationService.createNotification(appointment.getUser().getUsername(), "Termin gelöscht", "Termin mit " + appointment.getPartner().getName() + " gelöscht", "/appointments");
        appointmentRepository.delete(appointment);
        return appointment;
    }

    public List<AppointmentDto> getAppointmentsForUser(Long userId) {
        log.info("Getting appointments for user {}", userId);
        List<Appointment> appointments = appointmentRepository.findAllByUserId(userId);
        log.info("Found {} appointments", appointments.size());
        List<AppointmentDto> appointmentDtos = new ArrayList<>();
        for (Appointment appointment : appointments) {
            appointmentDtos.add(AppointmentDto.fromAppointment(appointment));
        }
        return appointmentDtos;
    }

    public List<AppointmentDto> getAppointmentsForPartner(Long partnerId) {
        log.info("Getting appointments for partner {}", partnerId);
        List<Appointment> appointments = appointmentRepository.findAllByPartnerId(partnerId);
        log.info("Found {} appointments", appointments.size());
        List<AppointmentDto> appointmentDtos = new ArrayList<>();
        for (Appointment appointment : appointments) {
            appointmentDtos.add(AppointmentDto.fromAppointment(appointment));
        }
        return appointmentDtos;
    }
}
