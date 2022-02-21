package blind.back.blind.domain.good.domain;

import blind.back.blind.domain.comment.domain.Comment;
import blind.back.blind.domain.post.domain.Post;
import blind.back.blind.domain.common.BaseTime;
import blind.back.blind.domain.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class Good extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "good_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @Builder
    public Good(Long id, User user, Post post, Comment comment) {
        this.id = id;
        this.user = user;
        this.post = post;
        this.comment = comment;
    }
}
