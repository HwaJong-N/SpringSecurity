package ghkwhd.oauth.oauth2.service;

import ghkwhd.oauth.member.domain.Member;
import ghkwhd.oauth.member.domain.MemberRole;
import ghkwhd.oauth.member.domain.SocialType;
import ghkwhd.oauth.member.repository.MemberRepository;
import ghkwhd.oauth.oauth2.domain.OAuth2Attributes;
import ghkwhd.oauth.oauth2.domain.OAuth2UserImpl;
import ghkwhd.oauth.oauth2.userInfo.OAuth2UserInfo;
import ghkwhd.oauth.oauth2.utils.OAuth2Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2UserServiceImpl extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        log.info("OAuth2Service loadUser() Start");

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();
        SocialType socialType = OAuth2Utils.getSocialType(registrationId);

        log.info("registrationId={}", registrationId);
        log.info("userNameAttributeName={}", userNameAttributeName);
        log.info("socialType={}", socialType);


        // 소셜에서 전달받은 정보를 가진 OAuth2User 에서 Map 을 추출하여 OAuth2Attribute 를 생성
        Map<String, Object> attributes = oAuth2User.getAttributes();

        // 내부에서 OAuth2UserInfo 생성과 함께 OAuth2Attributes 를 생성해서 반환
        OAuth2Attributes oauth2Attributes = OAuth2Attributes.of(socialType, userNameAttributeName, attributes);

        // Member 생성을 위한 정보를 가지고 있는 OAuth2UserInfo
        OAuth2UserInfo oauth2UserInfo = oauth2Attributes.getOauth2UserInfo();
        String socialId = oauth2UserInfo.getSocialId();
        String email = oauth2UserInfo.getEmail();

        log.info("socialId={}", socialId);
        log.info("email={}", email);

        // 소셜 타입과 소셜 ID 로 조회된다면 이전에 로그인을 한 유저
        // DB 에 조회되지 않는다면 Role 을 GUEST 로 설정하여 반환 => LoginSuccessHandler 에서 회원가입으로 리다이렉트 후 추가 정보를 받는다
        Member member = memberRepository.findBySocialTypeAndSocialId(socialType, socialId)
                .orElse(Member.builder().email(email).role(MemberRole.GUEST).socialType(socialType).socialId(socialId).build());

        return new OAuth2UserImpl(Collections.singleton(new SimpleGrantedAuthority(member.getRole().getValue())),
                attributes, oauth2Attributes.getNameAttributeKey(), member);
    }
}
