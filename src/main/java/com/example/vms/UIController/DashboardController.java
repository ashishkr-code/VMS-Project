package com.example.vms.UIController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model){
        return "admin/dashboard"; // templates/admin/dashboard.html
    }

    @GetMapping("/auditor/dashboard")
    public String auditorDashboard(Model model){
        return "auditor/dashboard"; // templates/auditor/dashboard.html
    }
}
