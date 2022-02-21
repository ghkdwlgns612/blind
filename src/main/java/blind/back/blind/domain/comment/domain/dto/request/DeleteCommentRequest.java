package blind.back.blind.domain.comment.domain.dto.request;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class DeleteCommentRequest implements Serializable {
    private Long comment_id;
    private Long post_id;
}
