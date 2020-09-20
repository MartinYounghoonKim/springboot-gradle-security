package com.inflearn.springsecurity.form;

import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SampleController {

    @GetMapping("/")
    public String index (Model model, Principal principal) {
        if (principal == null) {
            model.addAttribute("message", "hello Spring security");
        } else {
            model.addAttribute("message", "hello" + principal.getName());
        }

        return "index";
    }
    @GetMapping("/info")
    public String info (Model model) {
        model.addAttribute("message", "information");
        return "info";
    }

    @GetMapping("/dashboard")
    public String dashboard (Model model, Principal principal) {
        model.addAttribute("message", "Hello dashboard" + principal.getName());
        return "dashboard";
    }

    @GetMapping("/admin")
    public String admin (Model model, Principal principal) {
        model.addAttribute("message", "Hello admin" + principal.getName());
        return "admin";
    }
}
