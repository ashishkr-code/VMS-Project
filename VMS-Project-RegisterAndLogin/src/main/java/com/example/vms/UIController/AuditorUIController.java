package com.example.vms.UIController;

import com.example.vms.Model.Auditor;
import com.example.vms.Model.CveModel;
import com.example.vms.Model.Product;
import com.example.vms.Repository.CveRepository;
import com.example.vms.Repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AuditorUIController {

    @Autowired
    private CveRepository cveRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/auditor/dashboard")
    public String showAuditorDashboard(Model model, HttpSession session) {

        //  Get the logged-in auditor from session
        Auditor loggedInAuditor = (Auditor) session.getAttribute("loggedInAuditor");

        //  If not logged in, redirect to login page
        if (loggedInAuditor == null) {
            return "redirect:/login";
        }

        //  Fetch CVE and Product data
        List<CveModel> cves = cveRepository.findAll();
        List<Product> products = productRepository.findAll();

        //  Count data
        long totalCves = cves.size();
        long activeCves = cves.stream()
                .filter(c -> c.getStatus().name().equalsIgnoreCase("ACTIVE"))
                .count();
        long totalProducts = products.size();

        //  Add all data to Thymeleaf model
        model.addAttribute("cveList", cves);
        model.addAttribute("productList", products);
        model.addAttribute("totalCves", totalCves);
        model.addAttribute("activeCves", activeCves);
        model.addAttribute("totalProducts", totalProducts);

        //  Add logged-in auditor info
        model.addAttribute("auditorName", loggedInAuditor.getUsername());
        model.addAttribute("auditorEmail", loggedInAuditor.getEmail());
        model.addAttribute("auditorRole", loggedInAuditor.getRole());
        model.addAttribute("auditorCreatedAt", loggedInAuditor.getCreated_at());

        return "auditor/dashboard"; //  your Thymeleaf page (templates/auditor/dashboard.html)
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // clear session
        return "redirect:/login?logout";
    }

}
