package blind.back.blind.domain.comment.controller;

import blind.back.blind.domain.comment.domain.dto.request.CreateCommentRequest;
import blind.back.blind.domain.comment.domain.dto.request.DeleteCommentRequest;
import blind.back.blind.domain.comment.domain.dto.request.UpdateCommentRequest;
import blind.back.blind.domain.comment.domain.dto.response.CommentResponse;
import blind.back.blind.domain.comment.service.CommentService;
import blind.back.blind.domain.post.domain.dto.response.PostDetailResponse;
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
@RequestMapping("/comment")
@Api
public class CommentController {

    private final JwtUtil jwtUtil;
    private final CommentService commentService;

    @GetMapping("/me")
    @Operation(summary = "내 댓글 조회", description = "내 댓글을 리스트로 뽑아옵니다.")
    public ResultResponseDto<List<CommentResponse>> myCommentController(HttpServletRequest request) {
        String id = getUserId(request);
        log.info("{}이 내 댓글들을 조회합니다.",id);
        List<CommentResponse> result = commentService.findMyComment(id);
        log.info("내 댓글 조회가 완료되었습니다.");
        return new ResultResponseDto<>(HttpStatus.OK.value() * 10,"댓글작성완료",result);
    }

    @PostMapping
    @Operation(summary = "댓글 작성", description = "댓글을 작성하고 댓글의 정보를 반환합니다.")
    public ResultResponseDto<PostDetailResponse> createCommentController(HttpServletRequest request, @RequestBody CreateCommentRequest commentRequest) {
        String id = getUserId(request);
        log.info("{}님이 댓글을 작성합니다.",id);
        PostDetailResponse result = commentService.createComment(id, commentRequest);
        log.info("댓글 작성이 완료되었습니다.");
        return new ResultResponseDto<>(HttpStatus.OK.value() * 10,"댓글작성완료",result);
    }

    @PutMapping
    @Operation(summary = "댓글 수정", description = "댓글 수정 후 수정한 내용을 반환합니다.")
    public ResultResponseDto<PostDetailResponse> updateCommentController(HttpServletRequest httpServletRequest, @RequestBody UpdateCommentRequest request) {
        String id = getUserId(httpServletRequest);
        log.info("{}님이 댓글을 수정합니다.",id);
        PostDetailResponse result = commentService.updateComment(request,Long.valueOf(id));
        log.info("댓글을 수정했습니다.");
        return new ResultResponseDto<>(HttpStatus.OK.value() * 10,"댓글수정완료",result);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "댓글 삭제", description = "댓글을 삭제하고 삭제된 내용을 반환합니다.")
    public ResultResponseDto<PostDetailResponse> deleteCommentController(HttpServletRequest httpServletRequest, @RequestBody DeleteCommentRequest request) {
        String id = getUserId(httpServletRequest);
        log.info("{}님이 댓글을 삭제합니다.",id);
        PostDetailResponse result = commentService.deleteComment(request, Long.valueOf(id));
        log.info("댓글을 삭제했습니다.");
        return new ResultResponseDto<>(HttpStatus.OK.value(),"댓글삭제완료",result);
    }

    private String getUserId(HttpServletRequest request) {
        String[] token = new String[2];
        try {
            token = request.getHeader("authorization").split(" ");
        } catch (NullPointerException e) {
            log.warn("NPE");
        }
        return jwtUtil.getUid(token[1]);
    }
}