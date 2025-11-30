package com.planetbooks.controllers.views;

import com.planetbooks.models.Client;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegisterController {

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("client", new Client());
        return "Register";
    }

}