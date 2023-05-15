package fom.pmse.crms.backend.service;

import fom.pmse.crms.backend.model.Notification;
import fom.pmse.crms.backend.payload.response.NotificationDto;
import fom.pmse.crms.backend.repository.NotificationRepository;
import fom.pmse.crms.backend.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "NotificationService")
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public Long getUnreadNotificationCount(String username) {
        log.info("Getting number of unread notifications for user {}", username);
        Long count = notificationRepository.countAllByUser_UsernameAndReadFalse(username);
        log.info("Found {} unread notifications for user {}", count, username);
        return count;
    }

    public List<NotificationDto> getNotificationsForUser(String username) {
        log.info("Getting notifications for user {}", username);
        List<Notification> notifications = notificationRepository.findAllByUser_Username(username).orElseThrow();
        List<NotificationDto> notificationDtos = notifications.stream().map(NotificationDto::fromNotification).toList();
        log.info("Found {} notifications for user {}", notifications.size(), username);
        return notificationDtos;
    }

    public List<NotificationDto> getUnreadNotificationsForUser(String username) {
        log.info("Getting unread notifications for user {}", username);
        List<Notification> notifications = notificationRepository.findAllByUser_UsernameAndReadFalse(username).orElseThrow();
        List<NotificationDto> notificationDtos = notifications.stream().map(NotificationDto::fromNotification).toList();
        log.info("Found {} unread notifications for user {}", notifications.size(), username);
        return notificationDtos;
    }

    public Notification createNotification(String username, String title, String subtitle, String url) {
        log.info("Creating notification for user {}", username);
        Notification notification = new Notification();
        notification.setTitle(title);
        notification.setSubtitle(subtitle);
        notification.setUrl(url);
        notification.setRead(false);
        notification.setTimestamp(java.time.LocalDateTime.now());
        notification.setUser(userRepository.findByUsername(username).orElseThrow());
        Notification savedNotification = notificationRepository.save(notification);
        log.info("Created notification with id {} for user {}", savedNotification.getId(), username);
        return notification;
    }

    public NotificationDto markNotificationAsRead(Long id) {
        String authorizedUserUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("Marking notification with id {} as read", id);
        Notification notification = notificationRepository.findById(id).orElseThrow();
        if (notification.getUser().getUsername().equals(authorizedUserUsername)) {
            notification.setRead(true);
            Notification savedNotification = notificationRepository.save(notification);
            log.info("Marked notification with id {} as read", id);
            return NotificationDto.fromNotification(savedNotification);
        }
        throw new IllegalArgumentException("Es ist nicht möglich die Benachrichtigung eines anderen Benutzers als gelesen zu ändern.");
    }
}
