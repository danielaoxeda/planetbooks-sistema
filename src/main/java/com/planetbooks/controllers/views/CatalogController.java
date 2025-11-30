package com.planetbooks.controllers.views;

import com.planetbooks.services.InventoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class CatalogController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/catalog")
    public String catalog(Model model) {

        model.addAttribute("starters", inventoryService.findByExam("STARTERS"));
        model.addAttribute("movers", inventoryService.findByExam("MOVERS"));
        model.addAttribute("flyers", inventoryService.findByExam("FLYERS"));
        model.addAttribute("ket", inventoryService.findByExam("KET"));

        return "catalog";
    }
}

