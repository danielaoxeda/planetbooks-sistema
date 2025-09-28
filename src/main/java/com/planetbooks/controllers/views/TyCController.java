package com.planetbooks.controllers.views;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TyCController {

    @GetMapping("/terminos-y-condiciones")
    public String terminosYCondiciones(Model model) {
        return "TyC";
    }
}
