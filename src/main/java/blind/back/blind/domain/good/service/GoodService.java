package blind.back.blind.domain.good.service;

import blind.back.blind.domain.comment.domain.Comment;
import blind.back.blind.domain.comment.repository.CommentRepository;
import blind.back.blind.domain.good.domain.Good;
import blind.back.blind.domain.good.domain.dto.request.CreateCommentGoodRequest;
import blind.back.blind.domain.good.domain.dto.request.CreatePostGoodRequest;
import blind.back.blind.domain.good.repository.GoodRepository;
import blind.back.blind.domain.post.domain.Post;
import blind.back.blind.domain.post.domain.dto.response.PostDetailResponse;
import blind.back.blind.domain.post.repository.PostRepository;
import blind.back.blind.domain.post.service.PostService;
import blind.back.blind.domain.user.domain.User;
import blind.back.blind.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class GoodService {

    private final GoodRepository goodRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final PostService postService;

    public PostDetailResponse createPostGood(CreatePostGoodRequest request, String id) {
        Optional<User> user = userRepository.findById(Long.valueOf(id));
        Optional<Post> post = postRepository.findById(request.getPost_id());
        Optional<Good> good = goodRepository.findByUserIdAndPostId(user.get().getId(), post.get().getId());
        if (good.isEmpty())
            goodRepository.save(getPostGood(post.get(), user.get()));
        else
            existGoodUpdate(good, request.is_good());
        return postService.findPostByIdNotCountView(request.getPost_id(),user.get().getId());
    }

    public PostDetailResponse createCommentGood(CreateCommentGoodRequest request, String id) {
        Optional<User> user = userRepository.findById(Long.valueOf(id));
        Optional<Comment> comment = commentRepository.findById(request.getComment_id());
        Optional<Good> good = goodRepository.findByUserIdAndCommentId(user.get().getId(), comment.get().getId());
        if (good.isEmpty())
            goodRepository.save(getCommentGood(comment.get(), user.get()));
        else
            existGoodUpdate(good, request.is_good());
        return postService.findPostByIdNotCountView(comment.get().getPost().getId(),user.get().getId());
    }

    private void existGoodUpdate(Optional<Good> good, boolean isGood) {
        if (!isGood) {
            good.get().setDeletedAt(LocalDateTime.now());
        } else {
            good.get().setDeletedAt(null);
        }
        goodRepository.save(good.get());
    }

    private Good getPostGood(Post post, User user) {
        return Good.builder()
                .comment(null)
                .post(post)
                .user(user)
                .build();
    }

    private Good getCommentGood(Comment comment, User user) {
        return Good.builder()
                .comment(comment)
                .post(null)
                .user(user)
                .build();
    }
}
