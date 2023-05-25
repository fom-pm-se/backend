package fom.pmse.crms.backend;

import fom.pmse.crms.backend.model.Notification;
import fom.pmse.crms.backend.security.model.CrmUser;
import fom.pmse.crms.backend.security.model.Role;

import java.time.LocalDateTime;

public class TestUtilHelper {
    public static String getTestRestTemplateUrl(int port, String path) {
        return "http://localhost:" + port + path;
    }

    public static String getTestRestTemplateUrl(int port, String path, String query) {
        return "http://localhost:" + port + path + "?" + query;
    }

    public static CrmUser testCrmUser(Long id) {
        LocalDateTime now = LocalDateTime.now();
        CrmUser crmUser = new CrmUser();
        crmUser.setId(id);
        crmUser.setUsername("username");
        crmUser.setPassword("password");
        crmUser.setFirstname("firstName");
        crmUser.setLastname("lastName");
        crmUser.setRole(Role.USER);
        crmUser.setEnabled(true);
        crmUser.setCreatedAt(now);
        crmUser.setUpdatedAt(now);
        return crmUser;
    }

    public static Notification testNotification(Long id, CrmUser user) {
        LocalDateTime now = LocalDateTime.now();

        Notification notification = new Notification();
        notification.setId(id);
        notification.setTitle("title");
        notification.setSubtitle("subtitle");
        notification.setUrl("url");
        notification.setRead(false);
        notification.setTimestamp(now);
        notification.setUser(user);
        return notification;
    }
}
