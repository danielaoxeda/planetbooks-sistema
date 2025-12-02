package com.planetbooks.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminDashboardController {

    /**
     * Muestra la página principal del dashboard de administrador.
     * * ¡¡ATENCIÓN!! ESTA RUTA ESTÁ TEMPORALMENTE DESPROTEGIDA PARA LA DEMOSTRACIÓN.
     * Permite el acceso directo sin iniciar sesión.
     */
    @GetMapping("/admin/dashboard")
    public String showAdminDashboard(Model model) {
        
        // Simulamos que el nombre del administrador está en el modelo
        model.addAttribute("adminName", "Profesor DEMO"); 
        
        // Retorna la vista HTML sin ninguna verificación de seguridad
        return "admin/dashboard"; 
    }
}