package com.example.vms.UIController;

import com.example.vms.Dto.CveResponse;
import com.example.vms.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserDashBoard {

    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        List<CveResponse> cves = userService.getAllCves();
        model.addAttribute("cves", cves);
        model.addAttribute("total", cves.size());

        return "user-dashboard";
    }
}
