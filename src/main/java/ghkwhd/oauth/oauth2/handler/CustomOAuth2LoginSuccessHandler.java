package ghkwhd.oauth.oauth2.handler;

import ghkwhd.oauth.jwt.constants.JwtConstants;
import ghkwhd.oauth.jwt.constants.JwtUtils;
import ghkwhd.oauth.jwt.domain.RefreshToken;
import ghkwhd.oauth.jwt.service.JwtService;
import ghkwhd.oauth.member.domain.Member;
import ghkwhd.oauth.member.domain.MemberRole;
import ghkwhd.oauth.oauth2.domain.OAuth2UserImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
public class CustomOAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        
        Member member = ((OAuth2UserImpl) authentication.getPrincipal()).getMember();
        String accessToken = JwtUtils.generateAccessToken(member);

        // 최초 로그인인 경우 추가 정보 입력을 위한 회원가입 페이지로 리다이렉트
        if (member.getRole().equals(MemberRole.GUEST)) {
            response.addHeader(JwtConstants.ACCESS, JwtConstants.JWT_TYPE + accessToken);
            String redirectURL = UriComponentsBuilder.fromUriString("http://localhost:8080/oauth2/signUp")
                    .queryParam("email", member.getEmail())
                    .queryParam("socialType", member.getSocialType())
                    .queryParam("socialId", member.getSocialId())
                    .build()
                    .encode(StandardCharsets.UTF_8)
                    .toUriString();
            getRedirectStrategy().sendRedirect(request, response, redirectURL);
        } else {
            String refreshToken = JwtUtils.generateRefreshToken(member);
            jwtService.save(new RefreshToken(refreshToken, member.getId()));

            response.addHeader(JwtConstants.ACCESS, JwtConstants.JWT_TYPE + accessToken);
            response.addHeader(JwtConstants.REFRESH, JwtConstants.JWT_TYPE + refreshToken);

            // 최초 로그인이 아닌 경우 로그인 성공 페이지로 이동
            String redirectURL = UriComponentsBuilder.fromUriString("http://localhost:8080/loginSuccess")
                    .build()
                    .encode(StandardCharsets.UTF_8)
                    .toUriString();
            getRedirectStrategy().sendRedirect(request, response, redirectURL);
        }
    }
}
