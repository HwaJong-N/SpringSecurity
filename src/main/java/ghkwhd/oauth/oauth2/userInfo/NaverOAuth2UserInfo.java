package ghkwhd.oauth.oauth2.userInfo;

import java.util.Map;

public class NaverOAuth2UserInfo extends OAuth2UserInfo {

    public static Map<String, Object> responseMap;  // 소셜로부터 받은 정보( attributes ) 에서 필요한 정보들이 담긴 JSON 추출

    public NaverOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
        responseMap = (Map<String, Object>) attributes.get("response");
    }


    // Naver 는 정보가 attributes 내부에 response 라는 key 의 value 로
    // value 내부에 ( key, value ) 형식으로 담겨져서 온다
    @Override
    public String getSocialId() {
        return String.valueOf(responseMap.get("id"));
    }

    @Override
    public String getEmail() {
        return String.valueOf(responseMap.get("email"));
    }
}
