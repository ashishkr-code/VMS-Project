package com.example.vms.UIController;

import com.example.vms.Dto.CveRequest;
import com.example.vms.Dto.ProductRequest;
import com.example.vms.Enum.CveStatus;
import com.example.vms.Enum.ProductStatus;
import com.example.vms.Enum.Role;
import com.example.vms.Enum.Severity;
import com.example.vms.Model.Auditor;
import com.example.vms.Service.AuditorService;
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

    @Autowired
    private AuditorService auditorService;

    // ✅ Helper method for admin authentication
    private boolean isAdmin(HttpSession session) {
        Auditor user = (Auditor) session.getAttribute("loggedInAuditor");
        return user != null && user.getRole() == Role.ROLE_ADMIN;
    }

    // ✅ Dashboard (Main Page)
    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        Auditor loggedInUser = (Auditor) session.getAttribute("loggedInAuditor");

        model.addAttribute("cveList", adminService.getAllCves());
        model.addAttribute("productList", adminService.getAllProducts());
        model.addAttribute("userList", adminService.getAllUser());

        model.addAttribute("totalCves", adminService.getAllCves().size());
        model.addAttribute("activeCves", adminService.countByStatus(CveStatus.ACTIVE));
        model.addAttribute("patchedCves", adminService.countByStatus(CveStatus.INACTIVE));
        model.addAttribute("totalUsers", adminService.getAllUser().size());
        model.addAttribute("totalProducts", adminService.getAllProducts().size());

        model.addAttribute("adminName", loggedInUser.getUsername());
        model.addAttribute("adminEmail", loggedInUser.getEmail());
        model.addAttribute("adminRole", loggedInUser.getRole());
        model.addAttribute("adminCreatedAt", loggedInUser.getCreated_at());

        model.addAttribute("newCve", new CveRequest());

        // Severity stats for chart visualization
        Map<String, Long> severityCountMap = new HashMap<>();
        for (Severity severity : Severity.values()) {
            long count = cveRepository.countBySeverity(severity);
            severityCountMap.put(severity.name(), count);
        }
        model.addAttribute("severityCountMap", severityCountMap);

        return "admin/dashboard";
    }

    // -------------------------------
    // ✅ CVE MANAGEMENT
    // -------------------------------
    @PostMapping("/save-cve")
    public String saveCve(@ModelAttribute("newCve") CveRequest request, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        try {
            adminService.saveCve(request);
        } catch (Exception e) {
            session.setAttribute("errorMsg", "Error saving CVE: " + e.getMessage());
        }
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/delete-cve/{id}")
    public String deleteCve(@PathVariable Integer id, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        try {
            adminService.deleteCve(id);
        } catch (Exception e) {
            session.setAttribute("errorMsg", e.getMessage());
        }
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/update-cve")
    public String updateCve(@RequestParam Integer id,
                            @RequestParam String packageName,
                            @RequestParam String severity,
                            @RequestParam CveStatus status,
                            HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        try {
            adminService.updateCve(id, packageName, severity, status);
        } catch (Exception e) {
            session.setAttribute("errorMsg", "Error updating CVE: " + e.getMessage());
        }
        return "redirect:/admin/dashboard";
    }

    // -------------------------------
    // ✅ PRODUCT MANAGEMENT
    // -------------------------------
    @PostMapping("/save-product")
    public String saveProduct(@ModelAttribute ProductRequest req, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        try {
            adminService.saveProduct(req);
        } catch (Exception e) {
            session.setAttribute("errorMsg", "Error saving product: " + e.getMessage());
        }
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/update-product")
    public String updateProduct(@RequestParam("id") Long id,
                                @RequestParam("name") String name,
                                @RequestParam("status") ProductStatus status,
                                HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        try {
            adminService.updateProduct(id, name, status);
        } catch (Exception e) {
            session.setAttribute("errorMsg", "Error updating product: " + e.getMessage());
        }
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/delete-product/{id}")
    public String deleteProduct(@PathVariable Long id, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        try {
            adminService.deleteProduct(id);
        } catch (Exception e) {
            session.setAttribute("errorMsg", "Error deleting product: " + e.getMessage());
        }
        return "redirect:/admin/dashboard";
    }

    // -------------------------------
    // ✅ USER MANAGEMENT (only edit/delete)
    // -------------------------------
    @GetMapping("/delete-user/{id}")
    public String deleteUser(@PathVariable Long id, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        try {
            auditorService.deleteAuditor(id);
        } catch (Exception e) {
            session.setAttribute("errorMsg", "Error deleting user: " + e.getMessage());
        }
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/update-user")
    public String updateUser(@RequestParam("id") Long id,
                             @RequestParam("username") String username,
                             @RequestParam("mobileno") Long mobileno,
                             @RequestParam("role") Role role,
                             HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        try {
            adminService.updateAuditor(id, username, mobileno, role);
        } catch (Exception e) {
            session.setAttribute("errorMsg", "Error updating user: " + e.getMessage());
        }
        return "redirect:/admin/dashboard";
    }
}
