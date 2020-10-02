package com.inflearn.springsecurity.config;

import org.springframework.cglib.proxy.NoOp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/", "info", "/account/**", "/h2-console/**").permitAll()   // 인증 여부와 상관없이 항상 접근을 허용해줌
                .mvcMatchers("/admin").hasRole("ADMIN") // ADMIN 권한을 가진 유저만 접근 허용해줌
                .anyRequest().authenticated();                   // 인증만 된다면 접근 허용해줌
//                .and()                                         // method 체이닝을 and 로 꼭 연결 하지 않아도 된다.

        http.formLogin();
        http.httpBasic();
    }

    @Bean
    public PasswordEncoder passwordEncoder () {
        // 현재는 사용하지 않지만 {noop} 이 추가되지 않는다.
//        return NoOpPasswordEncoder.getInstance();
        // 기본값은 bcrypt 로 저장된다.
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        // {noop} prefix 를 사용하면 기본 패스워드 인코더를 사용하지 않고 DB에 저장한다.
//        auth.inMemoryAuthentication()
//                .withUser("martin").password("{noop}123").roles("USER").and()
//                .withUser("admin").password("{noop}!@#").roles("ADMIN");
//    }
}
