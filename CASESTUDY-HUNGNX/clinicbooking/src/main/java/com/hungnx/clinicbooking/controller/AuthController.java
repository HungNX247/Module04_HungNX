package com.hungnx.clinicbooking.controller;

import com.hungnx.clinicbooking.entity.User;
import com.hungnx.clinicbooking.service.AuthService;
import com.hungnx.clinicbooking.service.UserService;
import com.hungnx.clinicbooking.web.form.ChangePasswordForm;
import com.hungnx.clinicbooking.web.form.RegisterForm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    @GetMapping("/login")
    public String login(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        if (!model.containsAttribute("registerForm")) {
            model.addAttribute("registerForm", new RegisterForm());
        }
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@Valid@ModelAttribute("registerForm") RegisterForm registerForm, BindingResult bindingResult) {
        if (!registerForm.getPassword().equals(registerForm.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword","match", "Mật khẩu nhập lại không khớp");
        }

        if (bindingResult.hasErrors()) {
            return "auth/register";
        }

        try {
            authService.register(registerForm);
            return "redirect:/login?registered";
        } catch (IllegalArgumentException e) {
            bindingResult.reject("globalError", e.getMessage());
            return "auth/register";
        }
    }

    @GetMapping("/change-password")
    public String changePasswordPage(Model model) {
        if (!model.containsAttribute("changePasswordForm")) {
            model.addAttribute("changePasswordForm", new ChangePasswordForm());
        }
        return "auth/change-password";
    }

    @PostMapping("change-password")
    public String changePassword(@AuthenticationPrincipal User user,
                                 @Valid@ModelAttribute("changePasswordForm") ChangePasswordForm form,
                                 BindingResult bindingResult,
                                 Authentication authentication,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {
        if (!form.getNewPassword().equals(form.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "match", "Mật khẩu nhập lại không khớp");
        }

        if (bindingResult.hasErrors()) {
            return "auth/change-password";
        }

        try {
            userService.changePassword(user.getId(), form);
            new SecurityContextLogoutHandler().logout(request, response, authentication);
            return "redirect:/login?passwordChanged";
        } catch (IllegalArgumentException e) {
            bindingResult.reject("globalError",e.getMessage());
            return "auth/change-password";
        }
    }
}
