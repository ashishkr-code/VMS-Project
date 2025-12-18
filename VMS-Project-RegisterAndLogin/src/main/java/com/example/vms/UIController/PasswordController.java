package com.example.vms.UIController;

import com.example.vms.UIService.ForgetPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PasswordController {

    @Autowired
    private ForgetPassword userService;

    @GetMapping("/forgot-password")
    public String showForgotPage() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgot(@RequestParam("email") String email, Model model) {
        try {
            userService.generateResetToken(email);
            model.addAttribute("message", "If this email exists, a reset link has been sent.");
        } catch (Exception e) {
            model.addAttribute("error", "Something went wrong. Try again later.");
        }
        return "forgot-password";
    }

    @GetMapping("/reset-password")
    public String showReset(@RequestParam("token") String token, Model model) {
        if (!userService.isValidToken(token)) {
            model.addAttribute("error", "Invalid or expired token.");
            return "forgot-password";
        }
        model.addAttribute("token", token);
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String processReset(@RequestParam String token,
                               @RequestParam String password,
                               @RequestParam String confirmPassword,
                               Model model) {
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match.");
            model.addAttribute("token", token);
            return "reset-password";
        }
        if (!userService.resetPassword(token, password)) {
            model.addAttribute("error", "Token expired or invalid.");
            return "forgot-password";
        }
        model.addAttribute("message", "Password successfully updated! You can now login.");
        return "login";
    }
}
