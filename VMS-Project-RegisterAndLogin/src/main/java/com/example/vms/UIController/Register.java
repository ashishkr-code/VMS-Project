package com.example.vms.UIController;

import com.example.vms.Dto.RegisterRequest;
import com.example.vms.Dto.RegisterResponse;
import com.example.vms.Enum.Role;
import com.example.vms.Service.AuditorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/vms")
public class Register {

    @Autowired
    private AuditorService auditorService;

    @GetMapping({"/", "/home"})
    public String landingPage() {
        return "landingpage"; // landing.html
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("RegisterRequest", new RegisterRequest());
        return "register"; // register.html
    }

    @PostMapping("/register")
    public String registerUser(
            @Valid @ModelAttribute("RegisterRequest") RegisterRequest registerRequest,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        // ✅ Step 1: Validate form
        if (result.hasErrors()) {
            model.addAttribute("error", "Please correct the highlighted errors.");
            return "register";
        }

        // ✅ Step 2: Password match
        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            model.addAttribute("error", "Passwords do not match.");
            return "register";
        }

        // ✅ Step 3: Company code check
        if (registerRequest.getRole() == Role.ROLE_ADMIN &&
                !"COMPANY2025".equals(registerRequest.getCompanyCode())) {
            model.addAttribute("error", "Invalid company code for Admin.");
            return "register";
        }

        // ✅ Step 4: Register user (Auditor/Admin)
        try {
            RegisterResponse saved;
            if (registerRequest.getRole() == Role.ROLE_ADMIN) {
                saved = auditorService.registerAdmin(registerRequest);
            } else {
                saved = auditorService.registerAuditor(registerRequest);
            }

            // ✅ Step 5: Redirect with message (email is already sent inside service)
            redirectAttributes.addFlashAttribute("message",
                    "Registration successful! Please check your email for verification.");

            return "redirect:/login";

        } catch (Exception e) {
            model.addAttribute("error", "Registration failed: " + e.getMessage());
            return "register";
        }
    }
}
