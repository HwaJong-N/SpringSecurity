package ghkwhd.oauth.jwt.filter;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import ghkwhd.oauth.jwt.constants.JwtConstants;
import ghkwhd.oauth.jwt.constants.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ghkwhd.oauth.jwt.constants.JwtUtils.verifyToken;
import static javax.servlet.http.HttpServletResponse.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String[] whitelist = {"/", "/login", "/loginHome", "/signUp", "/renew", "/loginSuccess",
                                            "/login/oauth2/code/**", "/oauth2/signUp", "/error", "/js/**"};

    // 필터를 거치지 않을 URL 을 설정하고, true 를 return 하면 바로 다음 필터를 진행하게 됨
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return PatternMatchUtils.simpleMatch(whitelist, requestURI);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {

        String header = request.getHeader(JwtConstants.JWT_HEADER);

        // 토큰이 없거나 정상적이지 않은 경우
        if (header == null || !header.startsWith(JwtConstants.JWT_TYPE)) {
            response.setStatus(SC_BAD_REQUEST);
            response.setContentType(APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("utf-8");
            new ObjectMapper().writeValue(response.getWriter(), "Token 이 존재하지 않습니다");
            return;
        }

        try {
            // 토큰 검증
            String token = JwtUtils.getTokenFromHeader(header);
            DecodedJWT decodedJWT = verifyToken(token);

            UsernamePasswordAuthenticationToken authenticationToken = JwtUtils.getAuthenticationToken(decodedJWT);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            // 이거 없으면 다음 실행 안됨!!
            doFilter(request, response, filterChain);

        } catch (TokenExpiredException e) {
            // 토큰 만료 시 발생하는 예외
            response.setStatus(SC_UNAUTHORIZED);
            response.setContentType(APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("utf-8");
            new ObjectMapper().writeValue(response.getWriter(), "Access Token 이 만료되었습니다.");
        } catch (Exception e) {
            response.setStatus(SC_BAD_REQUEST);
            response.setContentType(APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("utf-8");
            new ObjectMapper().writeValue(response.getWriter(), "올바르지 않은 Token 입니다.");
        }
    }
}
