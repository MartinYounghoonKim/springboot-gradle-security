package com.inflearn.springsecuritycoreproject.demo.controller.user;

import com.inflearn.springsecuritycoreproject.demo.dto.AccountDto;
import com.inflearn.springsecuritycoreproject.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/mypage")
    public String main () {
        return "user/mypage";
    }

    @GetMapping
    public String creatAccount () {
        return "user/login/register";
    }

    @PostMapping
    public String createAccount (AccountDto accountDto) {
        userService.createUser(accountDto);
        return "redirect:/";
    }


}
