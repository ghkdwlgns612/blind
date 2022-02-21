package blind.back.blind.domain.notification.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PageNotificationResponse {
    private Long notification_id;
    private Long post_id;
    private String type;
    private Long parent_id;
    private String comment_content;
    private LocalDateTime created_at;
    private LocalDateTime deleted_at;

    @Builder
    public PageNotificationResponse(Long notification_id, Long post_id, String type, Long parent_id, String comment_content, LocalDateTime created_at, LocalDateTime deleted_at) {
        this.notification_id = notification_id;
        this.post_id = post_id;
        this.type = type;
        this.parent_id = parent_id;
        this.comment_content = comment_content;
        this.created_at = created_at;
        this.deleted_at = deleted_at;
    }
}
