package com.inflearn.springsecurity.form;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SampleController {

    @GetMapping("/")
    public String index (Model model) {
        model.addAttribute("message", "hello Spring security");
        return "index";
    }
}
