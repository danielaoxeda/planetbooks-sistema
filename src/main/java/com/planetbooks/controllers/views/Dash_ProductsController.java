package com.planetbooks.controllers.views;

import com.planetbooks.services.InventoryService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Dash_ProductsController {

    private final InventoryService inventoryService;

    public Dash_ProductsController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/dash-products")
    public String catalog(HttpServletRequest request, Model model) {
        model.addAttribute("currentPath", request.getRequestURI());

        // Get the products separated by levels
        model.addAttribute("starters", inventoryService.findByExam("STARTERS"));
        model.addAttribute("movers", inventoryService.findByExam("MOVERS"));
        model.addAttribute("flyers", inventoryService.findByExam("FLYERS"));
        model.addAttribute("ket", inventoryService.findByExam("KET"));

        return "admin/dash-products";
    }
}
