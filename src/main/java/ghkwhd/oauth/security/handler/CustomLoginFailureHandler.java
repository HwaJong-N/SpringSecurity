package ghkwhd.oauth.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


/**
 * 로그인 시 예외 처리를 위한 클래스
 * InputNotFoundException, PasswordNotMatchException, UserNotFoundException 모두 AuthenticationException 을 상속하기 때문에
 * 이곳에서 예외를 처리한다
 */
@Slf4j
public class CustomLoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        log.info("로그인 실패 = {}", exception.getMessage());
        response.setStatus(SC_BAD_REQUEST);
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        new ObjectMapper().writeValue(response.getWriter(), exception.getMessage());
    }
}
