package blind.back.blind.domain.notification.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class NotificationCountResponse {
    private Integer number;

    @Builder
    public NotificationCountResponse(Integer number) {
        this.number = number;
    }
}
