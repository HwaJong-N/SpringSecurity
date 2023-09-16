package ghkwhd.oauth.security.config;

import ghkwhd.oauth.security.domain.UserDetailsImpl;
import ghkwhd.oauth.security.exception.PasswordNotMatchException;
import ghkwhd.oauth.security.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    public final UserDetailsServiceImpl userDetailsService;
    public final BCryptPasswordEncoder passwordEncoder;

    // 인증 처리 수행
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        String id = token.getName();
        String password = (String) token.getCredentials();
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(id);
        // 패스워드가 일치하지 않는 경우
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new PasswordNotMatchException("비밀번호가 일치하지 않습니다");
        }
        // 인증에 성공했다면 성공한 인증 토큰을 반환
        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
