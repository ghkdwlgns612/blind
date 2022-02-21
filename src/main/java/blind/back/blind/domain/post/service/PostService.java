package blind.back.blind.domain.post.service;

import blind.back.blind.domain.comment.domain.Comment;
import blind.back.blind.domain.comment.domain.dto.response.PostCommentResponse;
import blind.back.blind.domain.comment.repository.CommentRepository;
import blind.back.blind.domain.good.domain.Good;
import blind.back.blind.domain.good.repository.GoodRepository;
import blind.back.blind.domain.post.domain.Post;
import blind.back.blind.domain.post.domain.dto.request.CreatePostRequest;
import blind.back.blind.domain.post.domain.dto.request.PagePostRequest;
import blind.back.blind.domain.post.domain.dto.request.UpdatePostRequest;
import blind.back.blind.domain.post.domain.dto.response.CreatePostResponse;
import blind.back.blind.domain.post.domain.dto.response.PagePostResponse;
import blind.back.blind.domain.post.domain.dto.response.PostDetailResponse;
import blind.back.blind.domain.post.domain.dto.response.UpdatePostResponse;
import blind.back.blind.domain.post.repository.PostRepository;
import blind.back.blind.domain.user.domain.User;
import blind.back.blind.domain.user.repository.UserRepository;
import blind.back.blind.global.error.Exception.BlankContentException;
import blind.back.blind.global.error.Exception.BlankTitleException;
import blind.back.blind.global.error.Exception.NotFoundPostException;
import blind.back.blind.global.error.Exception.NotFoundUserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final GoodRepository goodRepository;

    public List<PagePostResponse> findPagePost(PagePostRequest request) {
        List<PagePostResponse> responses = new ArrayList<>();
        postRepository.findAll(PageRequest.of(request.getPage(), request.getSize())).getContent()
                .forEach(p -> responses.add(getPagePostBuilder(p,p.getGoods().size(), p.getComments().size())));
        return responses;
    }

    public List<PagePostResponse> findPagePostPopular() {
        List<PagePostResponse> responses = new ArrayList<>();
        Optional<Post> post = postRepository.findById(1L);
        responses.add(getPagePostBuilder(post.get(),post.get().getGoods().size(),post.get().getComments().size()));
        postRepository.findTop3ByViews(PageRequest.of(0,2), LocalDateTime.now().minusDays(3L))
                .forEach(p -> responses.add(getPagePostBuilder(p, p.getGoods().size(),p.getComments().size())));
        return responses;
    }

    public List<PagePostResponse> findMyPost(String id) {
        List<PagePostResponse> responses = new ArrayList<>();
        Optional<User> user = userRepository.findById(Long.valueOf(id));
        if (user.isEmpty())
            throw new NotFoundUserException();
        postRepository.findAllByUser(user.get())
                .forEach(p -> responses.add(getPagePostBuilder(p, p.getGoods().size(),p.getComments().size())));
        return responses;
    }

    public PostDetailResponse findPostByIdNotCountView(Long postId, Long userId) {
        Optional<Post> init_post = postRepository.findById(postId);
        if (init_post.isEmpty())
            throw new NotFoundPostException();
        Optional<Post> post = postRepository.findById(postId);
        Integer goodCount = goodRepository.countAllByPostId(postId);
        List<PostCommentResponse> comments = findPostComment(post.get().getComments(), postId, userId);
        return getDetailPostBuilder(post.get(), goodCount, comments, userId);
    }


    public PostDetailResponse findPostById(Long postId, Long userId) {
        Optional<Post> init_post = postRepository.findById(postId);
        if (init_post.isEmpty())
            throw new NotFoundPostException();
        if (userId != init_post.get().getUser().getId()) {
            init_post.get().setViews(init_post.get().getViews() + 1);
            postRepository.save(init_post.get());
        }
        Optional<Post> post = postRepository.findById(postId);
        Integer goodCount = goodRepository.countAllByPostId(postId);
        List<PostCommentResponse> comments = findPostComment(post.get().getComments(), postId, userId);
        return getDetailPostBuilder(post.get(), goodCount, comments, userId);
    }

    public CreatePostResponse createPost(CreatePostRequest request, String id) {
        if (request.getContent().replace(" ","").equals(""))
            throw new BlankContentException();
        if (request.getTitle().replace(" ","").equals(""))
            throw new BlankTitleException();
        Optional<User> user = userRepository.findById(Long.valueOf(id));
        if (user.isEmpty())
            throw new NotFoundPostException();
        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .user(user.get())
                .views(0L)
                .build();
        Post result = postRepository.save(post);
        return new CreatePostResponse(result.getId(), result.getUser().getId());
    }

    public UpdatePostResponse updatePost(UpdatePostRequest request) {
        if (request.getContent().replace(" ","").equals(""))
            throw new BlankContentException();
        if (request.getTitle().replace(" ","").equals(""))
            throw new BlankTitleException();
        Post post = postRepository.findById(request.getPost_id()).get();
        post.setContentAndTitle(request);
        post = postRepository.save(post);
        return getUpdatePostBuilder(post, post.getGoods().size());
    }

    public Long deletePost(Long post_id) {
        Post post = postRepository.findById(post_id).get();
        post.setDeletedAt(LocalDateTime.now());
        postRepository.save(post);
        return post_id;
    }

    private List<PostCommentResponse> findPostComment(List<Comment> comments, Long postId, Long userId) {
        List<PostCommentResponse> responses = new ArrayList<>();
        List<Comment> commentGood = commentRepository.findAllByPostId(postId);
        Integer cnt = 0;
        for (int i=0; i<comments.size(); i++) {
            responses.add(getPostComment(commentGood.get(i).getGoods(), comments.get(i),userId));
            cnt++;
        }
        for (int i = cnt; i<commentGood.size(); i++) {
            responses.add(getPostModComment(commentGood.get(i)));
        }
        return responses;
    }

    private PostCommentResponse getPostModComment(Comment comment) {
        return PostCommentResponse.builder()
                .comment_id(comment.getId())
                .user_id(comment.getUser().getId())
                .goods(0)
                .modified_at(comment.getModifiedAt())
                .created_at(comment.getCreatedAt())
                .deleted_at(comment.getDeletedAt())
                .content(comment.getContent())
                .good_flag(false)
                .parent_id(comment.getParentId())
                .build();
    }

    private PostCommentResponse getPostComment(List<Good> goods, Comment commentPost, Long userId) {
        boolean isGood = false;
        int cnt = 0;
        for (Good good: goods) {
            if ((Objects.equals(good.getUser().getId(), userId))) {
                if (good.getDeletedAt() == null) {
                    isGood = true;
                }
            }
            if (good.getDeletedAt() != null)
                cnt++;
        }
        return PostCommentResponse.builder()
                .comment_id(commentPost.getId())
                .user_id(commentPost.getUser().getId())
                .goods(goods.size() - cnt)
                .modified_at(commentPost.getModifiedAt())
                .created_at(commentPost.getCreatedAt())
                .deleted_at(commentPost.getDeletedAt())
                .content(commentPost.getContent())
                .good_flag(isGood)
                .parent_id(commentPost.getParentId())
                .build();
    }

    private PagePostResponse getPagePostBuilder(Post post, Integer goods, Integer comment) {
        int cnt = 0;
        for (Good good : post.getGoods()) {
            if (good.getDeletedAt() != null) {
                cnt++;
            }
        }
        return PagePostResponse.builder()
                .post_id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .views(post.getViews())
                .goods(goods - cnt)
                .comment_number(comment)
                .user_id(post.getUser().getId())
                .created_at(post.getCreatedAt())
                .modified_at(post.getModifiedAt())
                .build();
    }

    private UpdatePostResponse getUpdatePostBuilder(Post post, Integer goods) {
        return UpdatePostResponse.builder()
                .post_id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .goods(Long.valueOf(goods))
                .views(post.getViews())
                .created_at(post.getCreatedAt())
                .modified_at(post.getModifiedAt())
                .user_id(post.getUser().getId())
                .build();
    }

    private PostDetailResponse getDetailPostBuilder(Post post, Integer goods, List<PostCommentResponse> comments, Long userId) {
        Optional<Good> isPost = goodRepository.findByUserIdAndPostId(userId, post.getId());
        boolean isGood = !isPost.isEmpty() && isPost.get().getDeletedAt() == null;
        return PostDetailResponse.builder()
                .post_id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .goods(Long.valueOf(goods))
                .views(post.getViews())
                .good_flag(isGood)
                .created_at(post.getCreatedAt())
                .modified_at(post.getModifiedAt())
                .user_id(post.getUser().getId())
                .comments(comments)
                .build();
    }
}
