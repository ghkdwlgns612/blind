package blind.back.blind.domain.good.controller;

import blind.back.blind.domain.good.domain.dto.request.CreateCommentGoodRequest;
import blind.back.blind.domain.good.domain.dto.request.CreatePostGoodRequest;
import blind.back.blind.domain.good.service.GoodService;
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

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/like")
@Api
public class GoodController {

    private final JwtUtil jwtUtil;
    private final GoodService goodService;

    @PostMapping("/post")
    @Operation(summary = "게시글 좋아요 작성", description = "게시글 좋아요를 만들어줍니다.")
    public ResultResponseDto<PostDetailResponse> createPostGoodsController(HttpServletRequest request, @RequestBody CreatePostGoodRequest goodRequest) {
        String id = getUserId(request);
        log.info("{}번 님이 게시글 좋아요를 눌렀습니다.", id);
        PostDetailResponse result = goodService.createPostGood(goodRequest, id);
        log.info("게시글 좋아요 완료");
        return new ResultResponseDto<>(HttpStatus.OK.value() * 10,"게시글 좋아요 완료",result);
    }

    @PostMapping("/comment")
    @Operation(summary = "댓글 좋아요 작성", description = "댓글 좋아요를 만들어줍니다.")
    public ResultResponseDto<PostDetailResponse> createCommentGoodsController(HttpServletRequest request, @RequestBody CreateCommentGoodRequest goodRequest) {
        String id = getUserId(request);
        log.info("{}님이 댓글 좋아요를 눌렀습니다.", id);
        PostDetailResponse result = goodService.createCommentGood(goodRequest, id);
        log.info("댓글 좋아요 완료");
        return new ResultResponseDto<>(HttpStatus.OK.value() * 10,"댓글 좋아요 완료",result);
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
