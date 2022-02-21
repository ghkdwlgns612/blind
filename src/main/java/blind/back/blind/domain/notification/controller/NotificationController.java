package blind.back.blind.domain.notification.controller;

import blind.back.blind.domain.notification.domain.dto.request.PageNotificationRequest;
import blind.back.blind.domain.notification.domain.dto.response.NotificationCountResponse;
import blind.back.blind.domain.notification.domain.dto.response.PageNotificationResponse;
import blind.back.blind.domain.notification.service.NotificationService;
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
@RequestMapping("/notification")
@Api
public class NotificationController {

    private final JwtUtil jwtUtil;
    private final NotificationService notificationService;

    @GetMapping("/count")
    @Operation(summary = "나의 읽지 않은 알림갯수",description = "첫 페이지 방문 시 읽지 않은 알림갯수를 보여줍니다.")
    public ResultResponseDto<NotificationCountResponse> notReadNotificationCountController(HttpServletRequest request) {
        String id = getUserId(request);
        NotificationCountResponse result = notificationService.getNotReadNotifications(id);
        log.info("{}번 유저님의 읽지 않은 알림이 {}개 있습니다.",id,result.getNumber());
        return new ResultResponseDto(HttpStatus.OK.value() * 10,"알림 갯수 체크완료",result);
    }

    @GetMapping("/page")
    @Operation(summary = "알림의 내용 및 연관관계 정보",description = "각 알림마다 포함하는 정보들을 보여줍니다.")
    public ResultResponseDto<NotificationCountResponse> pageNotificationController(HttpServletRequest request,
                                                                                   @RequestParam Integer page,
                                                                                   @RequestParam Integer size) {
        String id = getUserId(request);
        PageNotificationRequest req = PageNotificationRequest.builder().page(page).size(size).build();
        List<PageNotificationResponse> result = notificationService.getPageNotifications(req, id);
        log.info("{}번 유저님이 알림정보를 조회하였습니다.",id);
        return new ResultResponseDto(HttpStatus.OK.value() * 10,"알림 정보 체크완료",result);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "알림 확인",description = "사용자가 알림을 확인하였을 때 정보를 업데이트합니다.")
    public ResultResponseDto deleteNotificationController(@RequestParam Long notification_id) throws Exception {
        notificationService.deleteNotification(notification_id);
        log.info("{}번 알림을 읽었습니다.",notification_id);
        return new ResultResponseDto(HttpStatus.OK.value() * 10, "알림 확인 완료", null);
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
