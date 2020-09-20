package com.inflearn.springsecurity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/", "info").permitAll()   // 인증 여부와 상관없이 항상 접근을 허용해줌
                .mvcMatchers("/admin").hasRole("ADMIN") // ADMIN 권한을 가진 유저만 접근 허용해줌
                .anyRequest().authenticated()                    // 인증만 된다면 접근 허용해줌
                .and()                                           // method 체이닝을 and 로 꼭 연결 하지 않아도 된다.
            .formLogin()
                .and()
            .httpBasic();
    }
}
