package com.hungnx.clinicbooking.controller;

import com.hungnx.clinicbooking.entity.User;
import com.hungnx.clinicbooking.enums.Role;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(@AuthenticationPrincipal User user) {
        if (user == null) {
            return "redirect:/login";
        }
        return user.getRole() == Role.ADMIN ? "redirect:/admin/appointments" : "redirect:/doctors";
    }

    @GetMapping("/403")
    public String accessDenied() {
        return "error/403";
    }
}
