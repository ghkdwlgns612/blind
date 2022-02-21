package blind.back.blind.domain.notification.domain;

import blind.back.blind.domain.comment.domain.Comment;
import blind.back.blind.domain.common.BaseTime;
import blind.back.blind.domain.notification.domain.dto.Type;
import blind.back.blind.domain.post.domain.Post;
import blind.back.blind.domain.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private Type type;

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
    public Notification(Type type, User user, Post post, Comment comment) {
        this.type = type;
        this.user = user;
        this.post = post;
        this.comment = comment;
    }
}
