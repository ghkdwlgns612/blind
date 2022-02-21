package blind.back.blind.domain.comment.repository;

import blind.back.blind.domain.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Comment save(Comment comment);

    @Query("SELECT DISTINCT c FROM Comment c" +
            " LEFT JOIN FETCH c.goods g" +
            " LEFT JOIN FETCH g.user u" +
            " WHERE c.post.id = :post_id")
    List<Comment> findAllByPostId(Long post_id);

    @Query("SELECT DISTINCT c FROM Comment c " +
            " LEFT JOIN FETCH c.goods g" +
            " WHERE c.user.id = :user_id AND c.deletedAt IS NULL ORDER BY c.createdAt DESC")
    List<Comment> findAllByUserId(Long user_id);
}
