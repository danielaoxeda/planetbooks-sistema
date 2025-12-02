package com.planetbooks.controllers;

import com.planetbooks.models.Admin;
import com.planetbooks.services.LoginService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;
    
    // (Asumiendo que tienes un ClientService para el login de clientes)

    // Muestra la vista de Login
    @GetMapping("/login")
    public String showLogin() {
        return "Login"; // Asume que la vista se llama 'Login.html'
    }

    // Procesa el formulario de Login (UNIFICADO)
    @PostMapping("/login")
    public String performLogin(@RequestParam String user, // Usamos 'user' para que coincida con tu Client.java
                               @RequestParam String password, 
                               HttpSession session, 
                               Model model) {

        // 1. INTENTO DE LOGIN DE ADMINISTRADOR
        Optional<Admin> adminOpt = loginService.authenticateAdmin(user, password);
        
        if (adminOpt.isPresent()) {
            // LOGIN DE ADMIN EXITOSO
            session.setAttribute("userRole", "ADMIN");
            session.setAttribute("adminName", adminOpt.get().getName());
            return "redirect:/admin/dashboard"; 
        }

        // 2. INTENTO DE LOGIN DE CLIENTE (Lógica omitida, pero iría aquí)
        /*
        if (clientService.authenticateClient(user, password).isPresent()) {
            session.setAttribute("userRole", "CLIENT");
            return "redirect:/home"; 
        }
        */

        // 3. LOGIN FALLIDO
        model.addAttribute("loginError", "Usuario o contraseña incorrectos.");
        return "Login";
    }
    
    // Controlador de Logout simple
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); 
        return "redirect:/login"; 
    }
}