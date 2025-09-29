package com.planetbooks.controllers.views;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import ch.qos.logback.core.model.Model;

@Controller
public class RegistroController {
    
    @GetMapping("/registro")
    public String crearCuenta(Model model) {
        return "Crear_cuenta";
    }
}
