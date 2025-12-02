package com.planetbooks.controllers.views;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CheckoutSuccessController {

    @GetMapping("/checkout/success")
    public String success(HttpSession session, Model model) {

        String email = (String) session.getAttribute("userEmail");

        if (email == null) {
            return "redirect:/login";
        }

        model.addAttribute("email", email);

        return "checkout-success";
    }
}
