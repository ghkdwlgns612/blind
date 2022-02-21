package blind.back.blind.domain.comment.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
public class CommentResponse implements Serializable {
    private Long comment_id;
    private String content;
    private Long parent_id;
    private Long post_id;
    private Long goods;
    @JsonProperty("is_good")
    private boolean good_flag;
    private LocalDateTime created_at;
    private LocalDateTime modified_at;

    @Builder
    public CommentResponse(Long comment_id, String content, Long parent_id, Long post_id, Long goods, boolean good_flag, LocalDateTime created_at, LocalDateTime modified_at) {
        this.comment_id = comment_id;
        this.content = content;
        this.parent_id = parent_id;
        this.post_id = post_id;
        this.goods = goods;
        this.good_flag = good_flag;
        this.created_at = created_at;
        this.modified_at = modified_at;
    }
}
