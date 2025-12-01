package com.planetbooks.controllers.views;

import com.planetbooks.models.Inventory;
import com.planetbooks.services.InventoryService;
import com.planetbooks.services.StorageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/dash-products")
public class Dash_ProductsController {

    private final InventoryService inventoryService;
    private final StorageService storageService;

    public Dash_ProductsController(InventoryService inventoryService,
                                   StorageService storageService) {
        this.inventoryService = inventoryService;
        this.storageService = storageService;
    }

    // LISTADO
    @GetMapping
    public String listProducts(HttpServletRequest request, Model model) {
        model.addAttribute("currentPath", request.getRequestURI());

        model.addAttribute("starters", inventoryService.findByExam("STARTERS"));
        model.addAttribute("movers", inventoryService.findByExam("MOVERS"));
        model.addAttribute("flyers", inventoryService.findByExam("FLYERS"));
        model.addAttribute("ket", inventoryService.findByExam("KET"));

        return "admin/dash-products";
    }

    // FORM AGREGAR
    @GetMapping("/add")
    public String addProductForm(Model model) {
        model.addAttribute("book", new Inventory());
        return "admin/product-form";
    }

    // GUARDAR PRODUCTO NUEVO
    @PostMapping("/add")
    public String saveProduct(
            @ModelAttribute("book") Inventory book,
            @RequestParam("imageFile") MultipartFile file
    ) {
        String fileName = storageService.store(file);

        if (fileName != null) {
            book.setImg(fileName);
        }

        inventoryService.save(book);
        return "redirect:/dash-products";
    }

    // EDIT BY ID
    @GetMapping("/edit/{id}")
    public String editProductForm(@PathVariable int id, Model model) {
        Inventory book = inventoryService.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));
        model.addAttribute("book", book);
        return "admin/product-form";
    }

    // GUARDAR CAMBIOS
    @PostMapping("/edit/{id}")
    public String updateProduct(
            @PathVariable int id,
            @ModelAttribute("book") Inventory updatedBook,
            @RequestParam("imageFile") MultipartFile file
    ) {
        Inventory existing = inventoryService.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // If a new file was uploaded → replace image
        if (!file.isEmpty()) {
            String newFileName = storageService.store(file);
            updatedBook.setImg(newFileName);
        } else {
            updatedBook.setImg(existing.getImg());
        }

        updatedBook.setId(id);
        inventoryService.save(updatedBook);
        return "redirect:/dash-products";
    }

    // DELETE
    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable int id) {
        inventoryService.deleteById(id);
        return "redirect:/dash-products";
    }

}
