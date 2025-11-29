package com.planetbooks.controllers.views;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutUsController {

    @GetMapping("/us")
    public String catalog(HttpServletRequest request, Model model) {
        model.addAttribute("currentPath", request.getRequestURI());
        return "Us";
    }
}
