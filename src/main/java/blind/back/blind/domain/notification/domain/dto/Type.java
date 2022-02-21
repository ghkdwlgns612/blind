package blind.back.blind.domain.notification.domain.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Type {
    COMMENT("COMMENT"),
    POST("POST");

    private final String type;
}
