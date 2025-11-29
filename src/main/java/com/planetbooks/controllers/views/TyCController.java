package com.planetbooks.controllers.views;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TyCController {

    @GetMapping("/terms-and-conditions")
    public String termsANDconditions(Model model) {
        return "T&C";
    }
}
