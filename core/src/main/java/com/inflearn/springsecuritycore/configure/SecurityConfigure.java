package com.inflearn.springsecuritycore.configure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity(debug = true) // debug 를 true 로 설정하면 세션의 생성 시점 및 요청에 대한 filter 정보 등을 확인할 수 있다.
public class SecurityConfigure extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user").password("{noop}1111").roles("USER");
        auth.inMemoryAuthentication().withUser("admin").password("{noop}1111").roles("ADMIN");
        auth.inMemoryAuthentication().withUser("sys").password("{noop}1111").roles("SYS");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated();
        // formLogin 활성화
        http.formLogin()
        .successHandler(new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
                throws IOException, ServletException {
                RequestCache requestCache = new HttpSessionRequestCache();
                SavedRequest savedRequest = requestCache.getRequest(request, response);
                String redirectUrl = savedRequest.getRedirectUrl();
                response.sendRedirect(redirectUrl);
            }
        });

        http.authorizeRequests()
            .antMatchers("/login").permitAll()
            .antMatchers("/user").hasRole("USER")
            .antMatchers("/admin/pay").hasRole("ADMIN")
            .antMatchers("/admin/**").access("hasRole('ADMIN') or hasRole('SYS')")
            .anyRequest().authenticated();

        // rememberMe 기능 활성화
        http.rememberMe()
                .rememberMeParameter("remember")        // 기본값은 remember-me
                // 만약 -1로 설정한다면 사용자가 웹 브라우저 종료 시 함께 사라지는 세션 쿠키로 설정
                .tokenValiditySeconds(60 * 60)          // 기본값은 14일.
                .alwaysRemember(true)                   // rememberMe 기능이 활성화되지 않아도 항상 실행
                .userDetailsService(userDetailsService);

        // 동시 세션 제어
        http.sessionManagement()
                // none: 인증 완료 시에도 세션 변경하지 않음
                // changeSessionId : 인증완료시 세션 변경
                .sessionFixation().changeSessionId()
                .maximumSessions(1)                     // 최대 허용 가능 세션 수, -1: 무제한
                // 아래와 같이 설정 시, A 브라우저에서 명시적으로 로그아웃 하지 않고 브라우저 종료 시, B 브라우저에서 세션 만료 전까지 로그인 불가능
                .maxSessionsPreventsLogin(true)         // 동시 로그인 차단(새로운 세션 허용 X), false: 기존 세션 만료(default)
                .expiredUrl("/expired");                // 세션이 만료된 경우 이동할 페이지

        http.exceptionHandling()
            .authenticationEntryPoint(new AuthenticationEntryPoint() {
                @Override
                public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
                    throws IOException, ServletException {
                    response.sendRedirect("/login");
                }
            })
            .accessDeniedHandler(new AccessDeniedHandler() {
                @Override
                public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
                    throws IOException, ServletException {
                    response.sendRedirect("/denied");
                }
            });
        // CSRF 보안 제어
        http.csrf().disable();                          // CSRF 비활성화 (기본 활성화)
    }
}
