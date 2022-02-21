package blind.back.blind.domain.post.domain.dto.response;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class CreatePostResponse implements Serializable {
    private Long post_id;
    private Long user_id;

    public CreatePostResponse(Long post_id, Long user_id) {
        this.post_id = post_id;
        this.user_id = user_id;
    }
}
