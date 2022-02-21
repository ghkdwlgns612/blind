package blind.back.blind.domain.comment.domain;

import blind.back.blind.domain.good.domain.Good;
import blind.back.blind.domain.post.domain.Post;
import blind.back.blind.domain.common.BaseTime;
import blind.back.blind.domain.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class Comment extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    private String content;

    private Long parentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToMany(mappedBy = "comment")
    private List<Good> goods;

    @Builder
    public Comment(Long id, String content, User user, Post post, List<Good> goods, @Nullable Long parentId) {
        this.id = id;
        this.content = content;
        this.user = user;
        this.post = post;
        this.goods = goods;
        this.parentId = parentId;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
