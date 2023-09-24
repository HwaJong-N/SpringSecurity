package ghkwhd.oauth.common.config;

import ghkwhd.oauth.common.interceptor.AccessPermissionCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AccessPermissionCheckInterceptor())
                .order(1)
                .excludePathPatterns("/", "/login", "/loginHome", "/signUp", "/renew", "/loginSuccess",
                        "/login/oauth2/code/**", "/oauth2/signUp", "/error", "/js/**");
    }
}
