package com.inflearn.springsecuritycore.configure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfigure extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated();
        // formLogin 활성화
        http.formLogin();

        // rememberMe 기능 활성화
        http.rememberMe()
                .rememberMeParameter("remember")        // 기본값은 remember-me
                // 만약 -1로 설정한다면 사용자가 웹 브라우저 종료 시 함께 사라지는 세션 쿠키로 설정
                .tokenValiditySeconds(60 * 60)          // 기본값은 14일.
                .alwaysRemember(true)                   // rememberMe 기능이 활성화되지 않아도 항상 실행
                .userDetailsService(userDetailsService);

        // 동시 세션 제어
        http.sessionManagement()
                .maximumSessions(1)                     // 최대 허용 가능 세션 수, -1: 무제한
                .maxSessionsPreventsLogin(true)         // 동시 로그인 차단(새로운 세션 허용 X), false: 기존 세션 만료(default)
                .expiredUrl("/expired");                // 세션이 만료된 경우 이동할 페이지
    }
}