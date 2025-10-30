package com.example.vms.UIController;

import com.example.vms.Model.Auditor;
import com.example.vms.Enum.Role;
import com.example.vms.UIService.Authentic;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    @Autowired
    private Authentic authenticService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String email,
                               @RequestParam String password,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {

        Auditor user = authenticService.authenticate(email, password);

        if (user == null) {
            redirectAttributes.addFlashAttribute("message", "❌ Invalid email or password.");
            return "redirect:/login";
        }

        session.setAttribute("loggedInAuditor", user);

        if (user.getRole() == Role.ROLE_ADMIN) {
            return "redirect:/admin/dashboard";
        } else if (user.getRole() == Role.ROLE_AUDITOR) {
            return "redirect:/auditor/dashboard";
        } else {
            redirectAttributes.addFlashAttribute("message", "Unknown role. Contact support.");
            return "redirect:/login";
        }

    }
}
