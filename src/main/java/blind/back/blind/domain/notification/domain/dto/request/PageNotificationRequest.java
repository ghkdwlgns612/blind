package blind.back.blind.domain.notification.domain.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PageNotificationRequest {
    private Integer page;
    private Integer size;

    @Builder
    public PageNotificationRequest(Integer page, Integer size) {
        this.page = page;
        this.size = size;
    }
}
