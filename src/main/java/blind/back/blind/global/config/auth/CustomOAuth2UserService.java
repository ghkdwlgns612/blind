package blind.back.blind.global.config.auth;

import blind.back.blind.domain.common.Role;
import blind.back.blind.domain.user.domain.User;
import blind.back.blind.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;

    @Override       //가져온 유저정보를 저장하는 부분
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registerationId = userRequest.getClientRegistration().getRegistrationId();
        Object id = oAuth2User.getAttributes().get("id");
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        OAuthAttributes attributes = OAuthAttributes.of(registerationId, userNameAttributeName, oAuth2User.getAttributes(), ((Number) id).longValue());

        saveOrUpate(attributes);

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(
                Role.USER.getKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private User saveOrUpate(OAuthAttributes attributes) {
        User user = userRepository.findUserByFtId(attributes.getId())
                .map(entity -> entity.update(attributes.getNickname(), attributes.getFullname(), attributes.getPicture(), attributes.getId()))
                .orElse(attributes.toEntity());
        return userRepository.save(user);
    }
}