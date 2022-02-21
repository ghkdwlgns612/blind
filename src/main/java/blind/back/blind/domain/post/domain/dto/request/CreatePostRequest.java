package blind.back.blind.domain.post.domain.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class CreatePostRequest implements Serializable {
    private String title;
    private String content;

    @Builder
    public CreatePostRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
