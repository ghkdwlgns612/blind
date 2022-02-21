package blind.back.blind.domain.post.repository;

import blind.back.blind.domain.post.domain.Post;
import blind.back.blind.domain.user.domain.User;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Post save(@NonNull Post post);

    @Query("SELECT p FROM Post p WHERE p.deletedAt IS NULL ORDER BY p.createdAt DESC")
    Page<Post> findAll(@NonNull Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.deletedAt IS NULL AND NOT p.id IN (1) AND p.createdAt >= :date ORDER BY p.views DESC")
    Page<Post> findTop3ByViews(@NonNull Pageable pageable, LocalDateTime date);

    @Query("SELECT p FROM Post p WHERE p.deletedAt IS NULL AND p.user = :user ORDER BY p.createdAt DESC")
    List<Post> findAllByUser(User user);

    @Query("SELECT p FROM Post p " +
            " LEFT JOIN FETCH p.comments c" +
            " WHERE p.id = :post_id AND p.deletedAt IS NULL")
    Optional<Post> findById(@NonNull Long post_id);
}
