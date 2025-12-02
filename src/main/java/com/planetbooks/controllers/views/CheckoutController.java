package com.planetbooks.controllers.views;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CheckoutController {

    @GetMapping("/checkout")
    public String checkout(HttpServletRequest request, HttpSession session, Model model) {
        // Check if the user is logged in
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        // To select the active menu
        model.addAttribute("currentPath", request.getRequestURI());


        return "Checkout";
    }
}
