package ghkwhd.oauth.oauth2.userInfo;

import java.util.Map;

public abstract class OAuth2UserInfo {

    protected Map<String, Object> attributes;   // 각 소셜에서 전달된 전체 정보

    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }
    
    // 소셜에서 제공받은 정보를 추출하기 위한 메서드
    public abstract String getSocialId();   // 소셜 식별 값 ( 소셜 내부에서 사용자 식별값 )
    public abstract String getEmail();  // 최초 로그인 시, 회원가입할 때 이메일 전달을 위해 사용
}
