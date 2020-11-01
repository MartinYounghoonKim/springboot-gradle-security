package com.inflearn.springsecuritycore.controller;

import javax.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {
    @GetMapping("/")
    public String index (HttpSession httpSession) {
        // ThreadLocal 에 있는 Context 가져 오는 방법
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        SecurityContext context = (SecurityContext) httpSession.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
        Authentication authentication1 = context.getAuthentication(); // authentication 와 동일하다
        return "home";
    }

    @GetMapping("thread")
    public String thread () {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            }
        }).start();
        return "thread";
    }

    @GetMapping("/user")
    public String user () {
        return "user";
    }

    @GetMapping("/admin")
    public String admin () {
        return "admin";
    }
    @GetMapping("/admin/pay")
    public String adminPay () {
        return "adminPay";
    }
}
