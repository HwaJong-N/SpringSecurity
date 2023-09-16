package ghkwhd.oauth.security.handler;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 인가 예외 처리
 * 액세스 권한이 없는 리소스에 접근할 경우 발생하는 예외( AccessDeniedException ) 를 처리
 */
@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.info("리소스에 접근할 수 없습니다");
//        String redirectUrl = "/accessDenied";
//        // 쿼리 파라미터로 에러 메세지를 넘긴다
//        String queryParameter = "?exception=" + accessDeniedException.getMessage() + "&status=" + HttpStatus.FORBIDDEN.value();
//        response.sendRedirect(redirectUrl + queryParameter);
    }
}
