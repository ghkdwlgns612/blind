package blind.back.blind.global.common;

import lombok.Builder;
import lombok.Getter;

@Getter
public class HealthCheckResponse {
    private Long user_id;

    @Builder
    public HealthCheckResponse(Long user_id) {
        this.user_id = user_id;
    }
}
