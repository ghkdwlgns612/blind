package blind.back.blind.domain.post.domain.dto.response;

import blind.back.blind.domain.comment.domain.dto.response.PostCommentResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostDetailResponse implements Serializable {
    private Long post_id;
    private String title;
    private String content;
    private Long views;
    private Long goods;
    private Long user_id;
    @JsonProperty("is_good")
    private boolean good_flag;
    private LocalDateTime created_at;
    private LocalDateTime modified_at;
    private List<PostCommentResponse> comments;

    @Builder
    public PostDetailResponse(Long post_id, String title, String content, Long views, Long goods, Long user_id, boolean good_flag, LocalDateTime created_at, LocalDateTime modified_at, List<PostCommentResponse> comments) {
        this.post_id = post_id;
        this.title = title;
        this.content = content;
        this.views = views;
        this.goods = goods;
        this.user_id = user_id;
        this.good_flag = good_flag;
        this.created_at = created_at;
        this.modified_at = modified_at;
        this.comments = comments;
    }
}
