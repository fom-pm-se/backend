package fom.pmse.crms.backend.controller;

import fom.pmse.crms.backend.model.Appointment;
import fom.pmse.crms.backend.payload.request.AppointmentDto;
import fom.pmse.crms.backend.security.model.CrmUser;
import fom.pmse.crms.backend.service.AppointmentService;
import fom.pmse.crms.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<AppointmentDto> createAppointment(@RequestBody AppointmentDto appointmentDto) {
        AppointmentDto response = appointmentService.createAppointment(appointmentDto);
        if (response == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(201).body(response);
    }

    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<?> deleteAppointment(@PathVariable("appointmentId") Long appointmentId) {
        Appointment response = appointmentService.deleteAppointment(appointmentId);
        if (response == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<AppointmentDto>> getAppointmentsForUser() {
        CrmUser user = userService.getCurrentCrmUser();
        List<AppointmentDto> response = appointmentService.getAppointmentsForUser(user.getId());
        if (response == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{partnerId}")
    public ResponseEntity<List<AppointmentDto>> getAppointmentsForPartner(@PathVariable("partnerId") Long partnerId) {
        List<AppointmentDto> response = appointmentService.getAppointmentsForPartner(partnerId);
        if (response == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(response);
    }
}
