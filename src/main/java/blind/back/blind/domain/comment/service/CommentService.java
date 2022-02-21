package blind.back.blind.domain.comment.service;

import blind.back.blind.domain.comment.domain.Comment;
import blind.back.blind.domain.comment.domain.dto.request.CreateCommentRequest;
import blind.back.blind.domain.comment.domain.dto.request.DeleteCommentRequest;
import blind.back.blind.domain.comment.domain.dto.request.UpdateCommentRequest;
import blind.back.blind.domain.comment.domain.dto.response.CommentResponse;
import blind.back.blind.domain.comment.repository.CommentRepository;
import blind.back.blind.domain.good.domain.Good;
import blind.back.blind.domain.notification.service.NotificationService;
import blind.back.blind.domain.post.domain.Post;
import blind.back.blind.domain.post.domain.dto.response.PostDetailResponse;
import blind.back.blind.domain.post.repository.PostRepository;
import blind.back.blind.domain.post.service.PostService;
import blind.back.blind.domain.user.domain.User;
import blind.back.blind.domain.user.repository.UserRepository;
import blind.back.blind.global.error.Exception.BlankContentException;
import blind.back.blind.global.error.Exception.NotFoundCommentException;
import blind.back.blind.global.error.Exception.NotFoundPostException;
import blind.back.blind.global.error.Exception.NotFoundUserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class CommentService {
    
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final PostService postService;
    private final PostRepository postRepository;
    private final NotificationService notificationService;

    public PostDetailResponse createComment(String id, CreateCommentRequest commentRequest) {
        if (commentRequest.getContent().replace(" ","").equals(""))
            throw new BlankContentException();
        Optional<User> user = userRepository.findById(Long.valueOf(id));
        Optional<Post> post = postRepository.findById(commentRequest.getPost_id());
        if (user.isEmpty())
            throw new NotFoundUserException();
        if (post.isEmpty())
            throw new NotFoundPostException();
        Comment comment = getComment(commentRequest, user.get(), post.get());
        Comment comment_tmp = commentRepository.save(comment);
        if (commentRequest.getParent_id() > 0) {
            Optional<Comment> parent = commentRepository.findById(comment.getParentId());
            if (parent.isEmpty())
                throw new NotFoundCommentException();
            if (!Long.valueOf(id).equals(parent.get().getUser().getId()))
                notificationService.createNotification(comment_tmp,parent.get().getUser(),post.get(),"COMMENT");
        } else {
            if (!Long.valueOf(id).equals(post.get().getUser().getId()))
                notificationService.createNotification(comment_tmp,post.get().getUser(),post.get(),"POST");
        }
        return postService.findPostByIdNotCountView(post.get().getId(), user.get().getId());
    }

    public PostDetailResponse updateComment(UpdateCommentRequest request, Long userId) {
        if (request.getContent().replace(" ","").equals(""))
            throw new BlankContentException();
        Comment comment = commentRepository.findById(request.getComment_id()).get();
        comment.setContent(request.getContent());
        commentRepository.save(comment);
        return postService.findPostByIdNotCountView(request.getPost_id(),userId);
    }

    public PostDetailResponse deleteComment(DeleteCommentRequest request, Long userId) {
        Comment comment = commentRepository.findById(request.getComment_id()).get();
        comment.setDeletedAt(LocalDateTime.now());
        commentRepository.save(comment);
        return postService.findPostByIdNotCountView(request.getPost_id(), userId);
    }

    public List<CommentResponse> findMyComment(String id) {
        List<CommentResponse> responses = new ArrayList<>();
        Optional<User> user = userRepository.findById(Long.valueOf(id));
        if (user.isEmpty())
            throw new NotFoundUserException();
        log.info("댓글 조회 : {}",commentRepository.findAllByUserId(user.get().getId()).size());
        commentRepository.findAllByUserId(user.get().getId())
                .forEach(c -> responses.add(getCommentResponse(c, c.getGoods(), user.get().getId())));
        log.info("DTO의 길이 : {}", responses.size());
        return responses;
    }

    private CommentResponse getCommentResponse(Comment comment, List<Good> goods, Long userId) {
        boolean isGood = true;
        int cnt = 0;
        for (Good good : goods) {
            if (Objects.equals(good.getUser().getId(), userId)) {
                if (good.getDeletedAt() == null)
                    isGood = false;
            }
            if (good.getDeletedAt() != null)
                cnt++;
        }
        return CommentResponse.builder()
                .comment_id(comment.getId())
                .parent_id(comment.getParentId())
                .content(comment.getContent())
                .post_id(comment.getPost().getId())
                .goods((long) goods.size() - cnt)
                .good_flag(isGood)
                .created_at(comment.getCreatedAt())
                .modified_at(comment.getModifiedAt())
                .build();
    }

    private Comment getComment(CreateCommentRequest commentRequest, User user, Post post) {
        return Comment.builder()
                .parentId(commentRequest.getParent_id())
                .content(commentRequest.getContent())
                .user(user)
                .post(post)
                .build();
    }
}
