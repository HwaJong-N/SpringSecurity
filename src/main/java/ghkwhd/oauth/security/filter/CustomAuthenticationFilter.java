package ghkwhd.oauth.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import ghkwhd.oauth.member.domain.Member;
import ghkwhd.oauth.security.exception.InputNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        UsernamePasswordAuthenticationToken authRequest;

        try {
            Member member = new ObjectMapper().readValue(request.getInputStream(), Member.class);
            authRequest = new UsernamePasswordAuthenticationToken(member.getId(), member.getPassword());
        } catch (IOException e) {
            throw new InputNotFoundException("입력이 올바르지 않습니다");
        }

        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}