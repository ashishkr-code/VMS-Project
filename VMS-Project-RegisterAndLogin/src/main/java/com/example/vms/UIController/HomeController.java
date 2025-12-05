package com.example.vms.UIController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Open login page when opening root URL on Render
    @GetMapping("/")
    public String root() {
        return "login";  // This returns login.html
    }

    @GetMapping("/home")
    public String home() {
        return "landingpage";  // If you want homepage later
    }
}
