package com.planetbooks.controllers.views;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reports")
public class Dash_ReportsController {

    @GetMapping("")
    public String salesReport(HttpServletRequest request, Model model) {
        model.addAttribute("currentPath", request.getRequestURI());
        model.addAttribute("activeTab", "sales");
        return "admin/reports/sales-report";
    }

    @GetMapping("/products")
    public String productsReports(HttpServletRequest request, Model model) {
        model.addAttribute("currentPath", request.getRequestURI());
        model.addAttribute("activeTab", "products");
        return "admin/reports/products-report";
    }

    @GetMapping("/clients")
    public String clientsReport(HttpServletRequest request, Model model) {
        model.addAttribute("currentPath", request.getRequestURI());
        model.addAttribute("activeTab", "clients");
        return "admin/reports/clients-report";
    }

    @GetMapping("/failures")
    public String failuresReport(HttpServletRequest request, Model model) {
        model.addAttribute("currentPath", request.getRequestURI());
        model.addAttribute("activeTab", "failures");
        return "admin/reports/failures-report";
    }
}
