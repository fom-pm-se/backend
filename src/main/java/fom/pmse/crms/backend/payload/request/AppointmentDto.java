package fom.pmse.crms.backend.payload.request;

import fom.pmse.crms.backend.model.Appointment;
import fom.pmse.crms.backend.payload.response.AuditableDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AppointmentDto extends AuditableDto {
    private Long id;
    private String title;
    private String description;
    private String location;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private Long partnerId;
    private Long userId;

    public static AppointmentDto fromAppointment(Appointment appointment) {
        AppointmentDto appointmentDto = new AppointmentDto();
        appointmentDto.setId(appointment.getId());
        appointmentDto.setTitle(appointment.getTitle());
        appointmentDto.setDescription(appointment.getDescription());
        appointmentDto.setLocation(appointment.getLocation());
        appointmentDto.setStartAt(appointment.getStartAt());
        appointmentDto.setEndAt(appointment.getEndAt());
        appointmentDto.setPartnerId(appointment.getPartner().getId());
        appointmentDto.setUserId(appointment.getUser().getId());
        appointmentDto.setCreatedByUsername(appointment.getCreatedBy());
        appointmentDto.setCreationTime(appointment.getCreationTime());
        appointmentDto.setUpdatedByUsername(appointment.getUpdatedByUser());
        appointmentDto.setUpdateTime(appointment.getUpdateTime());
        return appointmentDto;
    }
}
