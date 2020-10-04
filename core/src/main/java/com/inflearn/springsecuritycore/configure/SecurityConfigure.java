package com.inflearn.springsecuritycore.configure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity(debug = true) // debug 를 true 로 설정하면 세션의 생성 시점 및 요청에 대한 filter 정보 등을 확인할 수 있다.
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
                // none: 인증 완료 시에도 세션 변경하지 않음
                // changeSessionId : 인증완료시 세션 변경
                .sessionFixation().changeSessionId()
                .maximumSessions(1)                     // 최대 허용 가능 세션 수, -1: 무제한
                // 아래와 같이 설정 시, A 브라우저에서 명시적으로 로그아웃 하지 않고 브라우저 종료 시, B 브라우저에서 세션 만료 전까지 로그인 불가능
                .maxSessionsPreventsLogin(true)         // 동시 로그인 차단(새로운 세션 허용 X), false: 기존 세션 만료(default)
                .expiredUrl("/expired");                // 세션이 만료된 경우 이동할 페이지
    }
}
