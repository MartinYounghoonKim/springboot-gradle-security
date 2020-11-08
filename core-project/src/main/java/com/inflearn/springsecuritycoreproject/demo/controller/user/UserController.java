package com.inflearn.springsecuritycoreproject.demo.controller.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    public String main () {
        return "user";
    }
}
