package blind.back.blind.domain.user.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

    private String fakename;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
