package blind.back.blind.global.config.auth;

import blind.back.blind.domain.user.domain.User;
import blind.back.blind.domain.user.repository.UserRepository;
import blind.back.blind.global.common.Token;
import blind.back.blind.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtils;
    private final UserRepository userRepository;
    @Value("#{new Long('${life.cookie.access}')}")
    private Long tokenPeriod;
    @Value("#{new Long('${life.cookie.refresh}')}")
    private Long refreshPeriod;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Optional<User> user = userRepository.findUserByNickname(oAuth2User.getAttribute("login"));
        log.info("유저이름 : {}", user.get().getFakename());
        Token token = jwtUtils.generateToken(user.get().getId());
        log.info("oauth success jwt = {}",token.getToken());
        log.info("oauth success refresh jwt = {}",token.getRefreshToken());
        ResponseCookie jwtCookie = ResponseCookie.from("jwt", token.getToken())
                .sameSite("Lax")
                .domain("42blind.com")
                .path("/")
                .httpOnly(false)
                .maxAge(tokenPeriod)
                .build();
        ResponseCookie refreshCookie = ResponseCookie.from("refresh", token.getRefreshToken())
                .sameSite("Lax")
                .domain("42blind.com")
                .path("/")
                .httpOnly(false)
                .maxAge(refreshPeriod)
                .build();
        response.addHeader("Set-cookie",jwtCookie.toString());
        response.addHeader("Set-cookie",refreshCookie.toString());
        response.sendRedirect("http://42blind.com");
    }
}