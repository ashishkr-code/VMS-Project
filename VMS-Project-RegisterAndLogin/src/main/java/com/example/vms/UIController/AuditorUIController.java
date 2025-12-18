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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class AuditorUIController {

    @Autowired
    private CveRepository cveRepository;

    @Autowired
    private ProductRepository productRepository;

    // ObjectMapper to convert Java data to JSON String for Thymeleaf/JavaScript
    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/auditor/dashboard")
    public String showAuditorDashboard(Model model, HttpSession session) {

        Auditor loggedInAuditor = (Auditor) session.getAttribute("loggedInAuditor");
        if (loggedInAuditor == null) {
            return "redirect:/login";
        }

        List<CveModel> cves = cveRepository.findAll();
        List<Product> products = productRepository.findAll();

        // 1. Static Counts (Existing)
        long totalCves = cves.size();
        long activeCves = cves.stream()
                .filter(c -> c.getStatus().name().equalsIgnoreCase("ACTIVE"))
                .count();
        long totalProducts = products.size();

        // 2. Dynamic Chart Data Calculation
        try {
            // --- Severity Distribution Chart Data ---
            Map<String, Long> severityCounts = cves.stream()
                    .collect(Collectors.groupingBy(c -> c.getSeverity().name(), Collectors.counting()));

            // Define desired order for labels
            List<String> orderedSeverities = List.of("LOW", "MEDIUM", "HIGH", "CRITICAL");

            // Filter and sort the counts based on the desired order
            List<String> severityLabels = orderedSeverities.stream()
                    .filter(severityCounts::containsKey)
                    .collect(Collectors.toList());

            List<Long> severityValues = severityLabels.stream()
                    .map(severityCounts::get)
                    .collect(Collectors.toList());

            // Convert to JSON strings for JavaScript
            model.addAttribute("severityLabels", objectMapper.writeValueAsString(severityLabels));
            model.addAttribute("severityValues", objectMapper.writeValueAsString(severityValues));


            // --- Status Distribution Chart Data ---
            Map<String, Long> statusCounts = cves.stream()
                    .collect(Collectors.groupingBy(c -> c.getStatus().name(), Collectors.counting()));

            List<String> statusLabels = statusCounts.keySet().stream().collect(Collectors.toList());
            List<Long> statusValues = statusLabels.stream().map(statusCounts::get).collect(Collectors.toList());

            model.addAttribute("statusLabels", objectMapper.writeValueAsString(statusLabels));
            model.addAttribute("statusValues", objectMapper.writeValueAsString(statusValues));


            // --- Product-wise CVE Count Chart Data ---
            Map<String, Long> productCveCounts = cves.stream()
                    .filter(c -> c.getProduct() != null)
                    .collect(Collectors.groupingBy(c -> c.getProduct().getProductId(), Collectors.counting()));

            // Sort by count (descending) and limit to top N products for better visualization
            List<String> productCveLabels = productCveCounts.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .limit(5) // Show only top 5 products
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());

            List<Long> productCveValues = productCveLabels.stream()
                    .map(productCveCounts::get)
                    .collect(Collectors.toList());

            model.addAttribute("productCveLabels", objectMapper.writeValueAsString(productCveLabels));
            model.addAttribute("productCveValues", objectMapper.writeValueAsString(productCveValues));

        } catch (JsonProcessingException e) {
            // Handle error (e.g., logging) if conversion fails, though unlikely for simple data
            System.err.println("Error processing JSON for charts: " + e.getMessage());
            // Optionally, add empty lists to the model as fallbacks
        }


        // 3. Add all data to Thymeleaf model (Existing)
        model.addAttribute("cveList", cves);
        model.addAttribute("productList", products);
        model.addAttribute("totalCves", totalCves);
        model.addAttribute("activeCves", activeCves);
        model.addAttribute("totalProducts", totalProducts);

        // Add logged-in auditor info (Existing)
        model.addAttribute("auditorName", loggedInAuditor.getUsername());
        model.addAttribute("auditorEmail", loggedInAuditor.getEmail());
        model.addAttribute("auditorRole", loggedInAuditor.getRole());
        model.addAttribute("auditorCreatedAt", loggedInAuditor.getCreated_at());

        return "auditor/dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout";
    }

}