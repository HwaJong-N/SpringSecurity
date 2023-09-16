package ghkwhd.oauth.config;

import ghkwhd.oauth.interceptor.AccessPermissionCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AccessPermissionCheckInterceptor())
                .order(1)
                .excludePathPatterns("/login", "/signUp", "/renew", "/js/**", "/");
    }
}
