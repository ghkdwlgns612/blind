package blind.back.blind.domain.comment.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
public class PostCommentResponse implements Serializable {
    private Long comment_id;
    private Integer goods;
    private String content;
    private Long parent_id;
    private Long user_id;
    @JsonProperty("is_good")
    private boolean good_flag;
    private LocalDateTime created_at;
    private LocalDateTime modified_at;
    private LocalDateTime deleted_at;

    @Builder
    public PostCommentResponse(Long comment_id, Integer goods, String content, Long parent_id, Long user_id, boolean good_flag, LocalDateTime created_at, LocalDateTime modified_at, LocalDateTime deleted_at) {
        this.comment_id = comment_id;
        this.goods = goods;
        this.content = content;
        this.parent_id = parent_id;
        this.user_id = user_id;
        this.good_flag = good_flag;
        this.created_at = created_at;
        this.modified_at = modified_at;
        this.deleted_at = deleted_at;
    }
}
