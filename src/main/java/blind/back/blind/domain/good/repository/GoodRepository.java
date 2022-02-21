package blind.back.blind.domain.good.repository;

import blind.back.blind.domain.good.domain.Good;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface GoodRepository extends JpaRepository<Good, Long> {
    Good save(@NonNull Good good);

    @Query("SELECT g FROM Good g WHERE g.user.id = :userId AND g.post.id = :postId")
    Optional<Good> findByUserIdAndPostId(Long userId, Long postId);

    @Query("SELECT g FROM Good g WHERE g.user.id = :userId AND g.comment.id = :commentId")
    Optional<Good> findByUserIdAndCommentId(Long userId, Long commentId);

    @Query("SELECT COUNT(g) FROM Good g WHERE g.post.id = :postId AND g.deletedAt IS NULL")
    Integer countAllByPostId(Long postId);

    @Query("SELECT COUNT(g) FROM Good g WHERE g.comment.id = :commentId AND g.deletedAt IS NULL")
    Integer countAllByCommentId(Long commentId);
}
