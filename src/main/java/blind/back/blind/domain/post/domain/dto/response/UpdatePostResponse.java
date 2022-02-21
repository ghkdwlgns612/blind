package blind.back.blind.domain.post.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
public class UpdatePostResponse implements Serializable{
    private Long post_id;
    private String title;
    private String content;
    private Long views;
    private Long goods;
    private Long user_id;
    private LocalDateTime created_at;
    private LocalDateTime modified_at;

    @Builder
    public UpdatePostResponse(Long post_id, String title, String content, Long views, Long goods, Long user_id, LocalDateTime created_at, LocalDateTime modified_at) {
        this.post_id = post_id;
        this.title = title;
        this.content = content;
        this.views = views;
        this.goods = goods;
        this.user_id = user_id;
        this.created_at = created_at;
        this.modified_at = modified_at;
    }
}
