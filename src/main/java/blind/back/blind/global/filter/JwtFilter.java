package blind.back.blind.global.filter;

import blind.back.blind.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter
@Component
@RequiredArgsConstructor
public class JwtFilter implements Filter {

    private final JwtUtil jwtUtil;
    @Value("#{new Long('${life.cookie.access}')}")
    private Long tokenPeriod;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        try {
            String[] token = servletRequest.getHeader("authorization").split(" ");
            if (token[0].equals("refresh")) {
                if (jwtUtil.verifyToken(token[1])) {
                    String userId = jwtUtil.getUid(token[1]);
                    ResponseCookie jwtCookie = ResponseCookie.from("jwt", jwtUtil.generateAccessToken(Long.valueOf(userId)))
                            .sameSite("Lax")
                            .domain("42blind.com")
                            .path("/")
                            .httpOnly(false)
                            .maxAge(tokenPeriod)
                            .build();
                    servletResponse.addHeader("Set-cookie", jwtCookie.toString());
                } else {
                    servletResponse.sendError(403, "리프레쉬토큰 만료");
                }
                return;
            } else {
                if (jwtUtil.verifyToken(token[1])) {
                    chain.doFilter(request, response);
                } else {
                    servletResponse.sendError(401, "액세스토큰 만료");
                    return;
                }
            }
        } catch (NullPointerException e) {
            if (servletRequest.getMethod().equals("OPTIONS"))
                servletResponse.sendError(200, "프리플라이트 완료");
            else if (servletRequest.getMethod().equals("GET"))
                servletResponse.sendError(404, "토큰이 없습니다.");
            return;
        }
    }
}
