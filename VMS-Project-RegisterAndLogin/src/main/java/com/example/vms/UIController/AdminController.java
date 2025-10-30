package com.example.vms.UIController;

import com.example.vms.Dto.CveRequest;
import com.example.vms.Dto.ProductRequest;
import com.example.vms.Enum.CveStatus;
import com.example.vms.Enum.ProductStatus;
import com.example.vms.Enum.Role;
import com.example.vms.Enum.Severity;
import com.example.vms.Model.Auditor;
import com.example.vms.UIService.AdminService;
import com.example.vms.Repository.CveRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private CveRepository cveRepository;



    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) { // <-- (1) YAHAN 'HttpSession session' ADD KAREIN


        // NAYA SECURITY CHECK BILKUL AUDITOR KI TARAH

        Auditor loggedInUser = (Auditor) session.getAttribute("loggedInAuditor");

        // Check 1: Kya user logged-in hai?
        if (loggedInUser == null) {
            return "redirect:/login"; // Login nahi hai, wapas bhejo
        }

        // Check 2: Kya user ADMIN hai?
        // (Yeh zaroori hai, kya pata Auditor /admin/dashboard kholne ki koshish kare)
        if (loggedInUser.getRole() != Role.ROLE_ADMIN) {
            return "redirect:/login"; // Admin nahi hai, wapas bhejo
        }

        model.addAttribute("cveList", adminService.getAllCves());
        model.addAttribute("productList", adminService.getAllProducts());
        model.addAttribute("userList", adminService.getAllUser());

        model.addAttribute("totalCves", adminService.getAllCves().size());
        model.addAttribute("activeCves", adminService.countByStatus(CveStatus.ACTIVE));
        model.addAttribute("patchedCves", adminService.countByStatus(CveStatus.INACTIVE));

        // Admin profile info (Ab session se le sakte hain)
        model.addAttribute("adminName", loggedInUser.getUsername());
        model.addAttribute("adminEmail", loggedInUser.getEmail());
        model.addAttribute("adminRole", loggedInUser.getRole());
        model.addAttribute("adminCreatedAt", loggedInUser.getCreated_at());

        model.addAttribute("newCve", new CveRequest());

        // Severity counts for charts (if you use them)
        Map<String, Long> severityCountMap = new HashMap<>();
        for (Severity severity : Severity.values()) {
            long count = cveRepository.countBySeverity(severity);
            severityCountMap.put(severity.name(), count);
        }
        model.addAttribute("severityCountMap", severityCountMap);

        return "admin/dashboard";
    }

    // -------------------
    // CVE Endpoints
    // -------------------
    @PostMapping("/save-cve")
    public String saveCve(@ModelAttribute("newCve") CveRequest request) {
        adminService.saveCve(request);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/delete-cve/{id}")
    public String deleteCve(@PathVariable Integer id) {
        adminService.deleteCve(id);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/update-cve")
    public String updateCve(@RequestParam Integer id,
                            @RequestParam String packageName,
                            @RequestParam String severity,
                            @RequestParam CveStatus status) {
        adminService.updateCve(id, packageName, severity, status);
        return "redirect:/admin/dashboard";
    }

    // -------------------
    // Product Endpoints
    // -------------------

    // Save product (add)
    @PostMapping("/save-product")
    public String saveProduct(@ModelAttribute ProductRequest req) {
        // req should contain at least 'name' and 'status' (ProductStatus)
        adminService.saveProduct(req);
        return "redirect:/admin/dashboard";
    }

    // Update product (edit)
    @PostMapping("/update-product")
    public String updateProduct(@RequestParam("id") Long id,
                                @RequestParam("name") String name,
                                @RequestParam("status") ProductStatus status) {
        adminService.updateProduct(id, name, status);
        return "redirect:/admin/dashboard";
    }

    // Delete product
    @GetMapping("/delete-product/{id}")
    public String deleteProduct(@PathVariable Long id) {
        adminService.deleteProduct(id);
        return "redirect:/admin/dashboard";
    }
}
