package ghkwhd.oauth.oauth2.userInfo;

import java.util.HashMap;
import java.util.Map;

public class KakaoOAuth2UserInfo extends OAuth2UserInfo {

    // 소셜로부터 받은 유저 정보( attributes ) 에서 필요한 정보들이 담긴 JSON 추출
    public static Map<String, Object> account;
    public static Map<String, Object> profile;

    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
        account = (Map<String, Object>) attributes.get("kakao_account");
        profile = (Map<String, Object>) account.get("profile");
    }

    @Override
    public String getSocialId() {
        return String.valueOf(attributes.get("id"));
    }

    @Override
    public String getEmail() {
        return (String) account.get("email");
    }   // 카카오는 이메일 없음 ( 필수 항목 X )
}
