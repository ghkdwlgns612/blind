package blind.back.blind.global.config.auth;

import blind.back.blind.domain.common.Role;
import blind.back.blind.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private Long id;
    private String nameAttributeKey;
    private String nickname;
    private String fullname;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes,Long id, String nameAttributeKey, String nickname,
                           String fullname, String email, String picture) {
        this.attributes = attributes;
        this.id = id;
        this.nameAttributeKey = nameAttributeKey;
        this.nickname = nickname;
        this.fullname = fullname;
        this.email = email;
        this.picture = picture;
    }

    public static OAuthAttributes of(String registerationId, String userNameAttributeName, Map<String, Object> attributes, Long id) {
        return ofIntra42(userNameAttributeName, attributes, id);
    }

    private static OAuthAttributes ofIntra42(String userNameAttributeName, Map<String, Object> attributes, Long id) {
        return OAuthAttributes.builder()
                .nickname((String) attributes.get("login"))
                .fullname((String) attributes.get("displayname"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("image_url"))
                .attributes(attributes)
                .id(id)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public User toEntity() {
        return User.builder()
                .nickname(nickname)
                .username(fullname)
                .email(email)
                .image(picture)
                .fakename("fortytwo" + Math.random() * 1000)
                .role(Role.USER)
                .ftId(id)
                .build();
    }
}