package blind.back.blind.domain.post.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
public class PagePostResponse implements Serializable {
    private Long post_id;
    private String title;
    private String content;
    private Long views;
    private Integer goods;
    private Integer comment_number;
    private Long user_id;
    private LocalDateTime created_at;
    private LocalDateTime modified_at;

    @Builder
    public PagePostResponse(Long post_id, String title, String content, Long views, Integer goods, Integer comment_number, Long user_id, LocalDateTime created_at, LocalDateTime modified_at) {
        this.post_id = post_id;
        this.title = title;
        this.content = content;
        this.views = views;
        this.goods = goods;
        this.comment_number = comment_number;
        this.user_id = user_id;
        this.created_at = created_at;
        this.modified_at = modified_at;
    }
}
