package blind.back.blind.domain.post.domain;

import blind.back.blind.domain.comment.domain.Comment;
import blind.back.blind.domain.common.BaseTime;
import blind.back.blind.domain.good.domain.Good;
import blind.back.blind.domain.post.domain.dto.request.UpdatePostRequest;
import blind.back.blind.domain.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class Post extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    private String title;

    private String content;

    private Long views;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    @OneToMany(mappedBy = "post")
    private List<Good> goods;

    @Builder
    public Post(Long id, String title, String content, Long views, User user, List<Comment> comments, List<Good> goods) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.views = views;
        this.user = user;
        this.comments = comments;
        this.goods = goods;
    }

    public void setViews(Long views) {
        this.views = views;
    }

    public void setContentAndTitle(UpdatePostRequest request) {
        this.title = request.getTitle();
        this.content = request.getContent();
    }
}
