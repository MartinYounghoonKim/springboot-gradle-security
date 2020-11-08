package com.inflearn.springsecuritycoreproject.demo.controller.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config")
public class ConfigController {
    public String main () {
        return "config";
    }
}
