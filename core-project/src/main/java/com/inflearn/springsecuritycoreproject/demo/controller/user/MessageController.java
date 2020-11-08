package com.inflearn.springsecuritycoreproject.demo.controller.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class MessageController {

    public String main () {
        return "message";
    }
}
