package ghkwhd.oauth.oauth2.domain;

import ghkwhd.oauth.member.domain.SocialType;
import ghkwhd.oauth.oauth2.userInfo.OAuth2UserInfo;
import ghkwhd.oauth.oauth2.utils.OAuth2Utils;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuth2Attributes {

    private String nameAttributeKey;    // OAuth2 로그인 시 사용자 정보가 담긴 key 값 ( ex> 네이버 = response )
    private OAuth2UserInfo oauth2UserInfo;

    @Builder
    public OAuth2Attributes(String nameAttributeKey, OAuth2UserInfo oauth2UserInfo) {
        this.nameAttributeKey = nameAttributeKey;
        this.oauth2UserInfo = oauth2UserInfo;
    }

    // OAuth2Utils 를 통해 분기처리 없이 생성된 OAuth2UserInfo 를 반환
    public static OAuth2Attributes of(SocialType socialType, String userNameAttributeName,
                                      Map<String, Object> attributes) {
        return OAuth2Attributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oauth2UserInfo(OAuth2Utils.getOAuth2UserInfo(socialType, attributes))
                .build();
    }
}
