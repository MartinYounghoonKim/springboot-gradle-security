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
                .rememberMeParameter("remember") // 기본값은 remember-me
                // 만약 -1로 설정한다면 사용자가 웹 브라우저 종료 시 함께 사라지는 세션 쿠키로 설정
                .tokenValiditySeconds(60 * 60)   // 기본값은 14일.
                .alwaysRemember(true)            // rememberMe 기능이 활성화되지 않아도 항상 실행
                .userDetailsService(userDetailsService);
    }
}
