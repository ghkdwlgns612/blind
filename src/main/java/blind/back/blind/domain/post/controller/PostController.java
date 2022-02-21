package blind.back.blind.domain.post.controller;

import blind.back.blind.domain.post.domain.dto.request.CreatePostRequest;
import blind.back.blind.domain.post.domain.dto.request.PagePostRequest;
import blind.back.blind.domain.post.domain.dto.request.UpdatePostRequest;
import blind.back.blind.domain.post.domain.dto.response.CreatePostResponse;
import blind.back.blind.domain.post.domain.dto.response.PagePostResponse;
import blind.back.blind.domain.post.domain.dto.response.PostDetailResponse;
import blind.back.blind.domain.post.domain.dto.response.UpdatePostResponse;
import blind.back.blind.domain.post.service.PostService;
import blind.back.blind.global.common.ResultResponseDto;
import blind.back.blind.global.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/post")
@Api
public class PostController {

    private final PostService postService;
    private final JwtUtil jwtUtil;

    @GetMapping
    @Operation(summary = "첫 페이지 관련 게시글 조회", description = "page와 size에 맞게 정보를 리턴. 프론트에서 중복되는 post_id확인")
    public ResultResponseDto<List<PagePostResponse>> pagePostController(@RequestParam Integer page, @RequestParam Integer size) {
        log.info("page : {} 와 size : {} 조회 접근",page,size);
        PagePostRequest request = PagePostRequest.builder().page(page).size(size).build();
        List<PagePostResponse> result = postService.findPagePost(request);
        log.info("page조회완료 했습니다.");
        return new ResultResponseDto<>(HttpStatus.OK.value() * 10, "페이지 조회완료",result);
    }

    @GetMapping("/popular")
    @Operation(summary = "인기 게시글 관련 조회", description = "인기순위 글 3개를 보여줍니다.")
    public ResultResponseDto<List<PagePostResponse>> pagePostPopularController() {
        log.info("인기 게시글 조회");
        List<PagePostResponse> result = postService.findPagePostPopular();
        log.info("인기 page조회완료 했습니다.");
        return new ResultResponseDto<>(HttpStatus.OK.value() * 10, "페이지 조회완료",result);
    }

    @GetMapping("/me")
    @Operation(summary = "내 게시글 조회", description = "JWT를 디코딩하여 게시물을 보여줍니다.")
    public ResultResponseDto<List<PagePostResponse>> myPostController(HttpServletRequest request) {
        String id = getUserId(request);
        log.info("{}님이 내 게시글을 조회합니다.",id);
        List<PagePostResponse> result = postService.findMyPost(id);
        log.info("내 게시글 조회완료.");
        return new ResultResponseDto<>(HttpStatus.OK.value() * 10, "내 게시글 조회완료",result);
    }

    @GetMapping("/detail")
    @Operation(summary = "게시글 상세 조회", description = "게시물글의 상세내용을 보여줍니다.")
    public ResultResponseDto<PostDetailResponse> detailPostController(HttpServletRequest request, @RequestParam Long post_id) {
        String id = getUserId(request);
        log.info("{}님이 {}번 글 상세정보를 조회합니다.",id,post_id);
        PostDetailResponse result = postService.findPostById(post_id, Long.valueOf(id));
        log.info("게시글 상세 조회 완료.");
        return new ResultResponseDto<>(HttpStatus.OK.value() * 10, "게시글 상세 조회완료",result);
    }

    @PostMapping
    @Operation(summary = "게시글 생성하기", description = "글을 생성합니다. 유저는 확인 후 작성가능합니다.")
    public ResultResponseDto<CreatePostResponse> createPostController(HttpServletRequest request, @RequestBody CreatePostRequest createPostRequest) {
        String id = getUserId(request);
        log.info("{}님이 글을 작성합니다.",id);
        CreatePostResponse result = postService.createPost(createPostRequest, id);
        log.info("글 작성 완료.");
        return new ResultResponseDto<>(HttpStatus.OK.value() * 10, "글 작성완료", result);
    }

    @PutMapping
    @Operation(summary = "게시글 수정하기", description = "게시글을 수정하고 수정된 내용을 반환합니다.")
    public ResultResponseDto<UpdatePostResponse> updatePostController(HttpServletRequest request, @RequestBody UpdatePostRequest updatePostRequest) {
        String id = getUserId(request);
        log.info("{}님이 {}번 글을 수정합니다.",id,updatePostRequest.getPost_id());
        UpdatePostResponse result = postService.updatePost(updatePostRequest);
        log.info("글 수정 완료");
        return new ResultResponseDto<>(HttpStatus.OK.value() * 10, "수정완료", result);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "게시글 삭제하기", description = "게시글을 삭제합니다.")
    public ResultResponseDto<Long> deleteBoardController(HttpServletRequest request, @RequestParam Long post_id) {
        String id = getUserId(request);
        log.info("{}님이 {}번 글을 삭제합니다.",id, post_id);
        Long result = postService.deletePost(post_id);
        log.info("글 삭제 완료");
        return new ResultResponseDto<>(HttpStatus.OK.value() * 10, "글 삭제완료",result);
    }

    private String getUserId(HttpServletRequest request) {
        String[] token = request.getHeader("authorization").split(" ");
        return jwtUtil.getUid(token[1]);
    }
}
