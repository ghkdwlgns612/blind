package blind.back.blind.domain.user.domain;

import blind.back.blind.domain.good.domain.Good;
import blind.back.blind.domain.post.domain.Post;
import blind.back.blind.domain.comment.domain.Comment;
import blind.back.blind.domain.common.BaseTime;
import blind.back.blind.domain.common.Role;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String nickname;

    @Column(unique = true)
    private String fakename;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String image;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Column(unique = true)
    private Long ftId;

    //댓글들(List<Comment>)
    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    //작성글들(List<Board>)
    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    //좋아요들()
    @OneToMany(mappedBy = "user")
    private List<Good> goods;

    @Builder
    public User(Long id, String username, String nickname, String fakename, String email, String image, Role role, Long ftId, List<Comment> comments, List<Post> posts, List<Good> goods) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.fakename = fakename;
        this.email = email;
        this.image = image;
        this.role = role;
        this.ftId = ftId;
        this.comments = comments;
        this.posts = posts;
        this.goods = goods;
    }

    public User update(String nickname, String username, String image, Long id) {
        this.nickname = nickname;
        this.username = username;
        this.image = image;
        this.ftId = id;
        return this;
    }
}
