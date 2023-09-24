package ghkwhd.oauth.security.config;

import ghkwhd.oauth.jwt.service.JwtService;
import ghkwhd.oauth.member.service.MemberService;
import ghkwhd.oauth.oauth2.handler.CustomOAuth2LoginFailureHandler;
import ghkwhd.oauth.oauth2.handler.CustomOAuth2LoginSuccessHandler;
import ghkwhd.oauth.oauth2.service.OAuth2UserServiceImpl;
import ghkwhd.oauth.security.filter.CustomAuthenticationFilter;
import ghkwhd.oauth.security.handler.CustomLoginFailureHandler;
import ghkwhd.oauth.security.handler.CustomLoginSuccessHandler;
import ghkwhd.oauth.security.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;
    private final OAuth2UserServiceImpl oauth2UserService;
    private final JwtService jwtService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager());
        customAuthenticationFilter.setFilterProcessesUrl("/login");
        customAuthenticationFilter.setAuthenticationSuccessHandler(customLoginSuccessHandler());
        customAuthenticationFilter.setAuthenticationFailureHandler(customLoginFailureHandler());
        customAuthenticationFilter.afterPropertiesSet();
        return customAuthenticationFilter;
    }

    @Bean
    public CustomLoginSuccessHandler customLoginSuccessHandler() {
        return new CustomLoginSuccessHandler(jwtService);
    }


    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider() {
        return new CustomAuthenticationProvider(userDetailsService, passwordEncoder());
    }

    @Bean
    public CustomLoginFailureHandler customLoginFailureHandler() {
        return new CustomLoginFailureHandler();
    }

    @Bean
    public CustomOAuth2LoginSuccessHandler customOAuth2LoginSuccessHandler() {
        return new CustomOAuth2LoginSuccessHandler(jwtService);
    }

    @Bean
    public CustomOAuth2LoginFailureHandler customOAuth2LoginFailureHandler() {
        return new CustomOAuth2LoginFailureHandler();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.authenticationProvider(customAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                        .anyRequest().permitAll()
             .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
             .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .logout().logoutSuccessUrl("/")
             .and()
                 .oauth2Login().userInfoEndpoint().userService(oauth2UserService)   // 구현한 OAuth2UserService 등록
             .and()
                .successHandler(customOAuth2LoginSuccessHandler())  // 로그인 성공 핸들러 등록
                .failureHandler(customOAuth2LoginFailureHandler());
    }
}