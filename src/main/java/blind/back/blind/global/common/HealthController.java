package blind.back.blind.global.common;

import blind.back.blind.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class HealthController {

    private final JwtUtil jwtUtil;

    @GetMapping
    public ResultResponseDto<HealthCheckResponse> healthCheckController(HttpServletRequest request) {
        HealthCheckResponse result = HealthCheckResponse.builder().user_id(Long.valueOf(getId(request))).build();
        return new ResultResponseDto<>(HttpStatus.OK.value() * 10, "헬스체크완료",result);
    }

    private String getId(HttpServletRequest request) {
        String[] token = request.getHeader("authorization").split(" ");
        return jwtUtil.getUid(token[1]);
    }
}