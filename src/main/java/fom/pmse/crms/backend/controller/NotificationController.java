package fom.pmse.crms.backend.controller;

import fom.pmse.crms.backend.payload.response.NotificationDto;
import fom.pmse.crms.backend.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/notification")
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/unread/count")
    @Operation(summary = "Get number of unread notifications for user")
    @ApiResponse(responseCode = "200", description = "Number of unread notifications")
    public ResponseEntity<Long> getNumberOfUnreadNotifications() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(notificationService.getUnreadNotificationCount(username));
    }

    @GetMapping("/unread/")
    public ResponseEntity<List<NotificationDto>> getUnreadNotifications() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(notificationService.getUnreadNotificationsForUser(username));
    }

    @GetMapping("/all")
    public ResponseEntity<List<NotificationDto>> getNotifications() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(notificationService.getNotificationsForUser(username));
    }

    @PutMapping("/{id}/")
    public ResponseEntity<NotificationDto> markNotificationAsRead(@PathVariable("id") Long id) {
        return ResponseEntity.ok(notificationService.markNotificationAsRead(id));
    }
}
