package blind.back.blind.domain.post.domain.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class UpdatePostRequest implements Serializable {
    private Long post_id;
    private String title;
    private String content;

    @Builder
    public UpdatePostRequest(Long post_id, String title, String content) {
        this.post_id = post_id;
        this.title = title;
        this.content = content;
    }
}
