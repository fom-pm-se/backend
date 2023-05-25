package fom.pmse.crms.backend.payload.response;

import fom.pmse.crms.backend.TestUtilHelper;
import fom.pmse.crms.backend.model.Notification;
import fom.pmse.crms.backend.security.model.CrmUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotificationDtoTest {

    Notification notification;
    NotificationDto notificationDto;

    @BeforeEach
    public void setUp() {
        notification = TestUtilHelper.testNotification(1L, new CrmUser());
        notificationDto = NotificationDto.fromNotification(notification);
    }

    @Test void fromNotificationReturnsCorrectNotificationDto() {
        assertEquals(notification.getId(), notificationDto.getId());
        assertEquals(notification.getTitle(), notificationDto.getTitle());
        assertEquals(notification.getSubtitle(), notificationDto.getSubtitle());
        assertEquals(notification.getTimestamp().toString(), notificationDto.getTimestamp());
        assertEquals(notification.isRead(), notificationDto.isRead());
    }
}