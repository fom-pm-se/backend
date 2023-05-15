package fom.pmse.crms.backend.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationDto {
    private long id;
    private String title;
    private String subtitle;
    private String timestamp;
    private String url;
    private boolean read;

    public static NotificationDto fromNotification(fom.pmse.crms.backend.model.Notification notification) {
        return NotificationDto.builder()
                .id(notification.getId())
                .title(notification.getTitle())
                .subtitle(notification.getSubtitle())
                .timestamp(notification.getTimestamp().toString())
                .url(notification.getUrl())
                .read(notification.isRead())
                .build();
    }
}
