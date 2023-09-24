package ghkwhd.oauth.common.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import ghkwhd.oauth.member.domain.MemberRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;

/**
 * 권한에 따른 처리를 하기 위한 Interceptor
 * @EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) 를 선언하고
 * Controller 에서 @PreAuthorize 를 사용해도 되지만, Controller 로 가기 전, Interceptor 에서 처리를 하는 것이 낫다고 생각함
 */

public class AccessPermissionCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (request.getRequestURI().startsWith("/admin")) {
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority(MemberRole.ADMIN.getValue()))) {
                return true;
            }
        } else {
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority(MemberRole.USER.getValue()))) {
                return true;
            }
        }
        response.setStatus(SC_FORBIDDEN);
        response.setCharacterEncoding("utf-8");
        new ObjectMapper().writeValue(response.getWriter(), "접근 권한이 없습니다.");
        return false;
    }
}
