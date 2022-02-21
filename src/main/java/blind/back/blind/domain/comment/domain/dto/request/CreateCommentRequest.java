package blind.back.blind.domain.comment.domain.dto.request;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class CreateCommentRequest implements Serializable {
    private String content;
    private Long post_id;
    private Long parent_id;
}
