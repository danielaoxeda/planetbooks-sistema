package com.planetbooks.controllers.views;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CuentaController {

    @GetMapping("/cuenta")
    public String cuenta(Model model) {
        return "Cuenta";
    }
}
