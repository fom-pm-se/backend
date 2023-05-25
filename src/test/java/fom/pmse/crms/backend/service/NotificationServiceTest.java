package fom.pmse.crms.backend.service;

import fom.pmse.crms.backend.TestUtilHelper;
import fom.pmse.crms.backend.model.Notification;
import fom.pmse.crms.backend.payload.response.NotificationDto;
import fom.pmse.crms.backend.repository.NotificationRepository;
import fom.pmse.crms.backend.security.model.CrmUser;
import fom.pmse.crms.backend.security.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class NotificationServiceTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationService notificationService;

    private CrmUser crmUser;

    @BeforeEach
    void setUp() {

        CrmUser crmUser = TestUtilHelper.testCrmUser(1L);
        CrmUser saved = userRepository.save(crmUser);

        this.crmUser = saved;

        notificationRepository.save(TestUtilHelper.testNotification(1L, saved));
        notificationRepository.save(TestUtilHelper.testNotification(2L, saved));
    }

    @AfterEach
    void tearDown() {
        notificationRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void unreadNotificationsCountReturnsCorrectCount() {
        Long count = notificationService.getUnreadNotificationCount(crmUser.getUsername());
        assertEquals(2L, count);
    }

    @Test
    public void unreadNotificationsCountReturnsZeroWhenNoNotifications() {
        notificationRepository.deleteAll();
        Long count = notificationService.getUnreadNotificationCount(crmUser.getUsername());
        assertEquals(0L, count);
    }

    @Test
    void unreadNotificationsCountReturnsZeroWhenAllNotificationsRead() {
        List<Notification> notifications = notificationRepository.findAll();
        notifications.forEach(notification -> notification.setRead(true));
        notificationRepository.saveAll(notifications);
        Long count = notificationService.getUnreadNotificationCount(crmUser.getUsername());
        assertEquals(0L, count);
    }

    @Test
    void unreadNotificationsCountReturnsZeroWhenUserDoesNotExist() {
        Long count = notificationService.getUnreadNotificationCount("doesNotExist");
        assertEquals(0L, count);
    }

    @Test void getNotificationsReturnsListOfNotificationDtos() {
        List<Notification> notifications = notificationRepository.findAllByUser_Username(crmUser.getUsername()).orElseThrow();
        List<NotificationDto> notificationDtos = notificationService.getNotificationsForUser(crmUser.getUsername());
        assertEquals(notifications.size(), notificationDtos.size());
    }
}