package com.planetbooks.controllers.views;

import com.planetbooks.models.Client;
import com.planetbooks.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.time.LocalDate;

@Controller
public class RegisterController {

    @Autowired
    private ClientRepository clientRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("client", new Client());
        return "Register"; // tu template HTML
    }

    @PostMapping("/register")
    public String registerClient(@ModelAttribute Client client, Model model) {
        // Validar si el usuario ya existe
        if(clientRepository.findByActiveTrue().stream().anyMatch(c -> c.getUser().equals(client.getUser()))) {
            model.addAttribute("error", "El nombre de usuario ya existe");
            return "Register";
        }
        // Validar si el email ya existe
        if(clientRepository.findByActiveTrue().stream().anyMatch(c -> c.getEmail().equals(client.getEmail()))) {
            model.addAttribute("error", "El correo ya está registrado");
            return "Register";
        }

        // Encriptar la contraseña
        client.setPassword(passwordEncoder.encode(client.getPassword()));

        // Setear datos por defecto
        client.setRole("CLIENT");
        client.setRegistration_date(LocalDate.now());
        client.setActive(true);
        client.setPurchases(0);
        client.setSessions(0);

        clientRepository.save(client);

        model.addAttribute("success", "Registro exitoso. Ya puedes iniciar sesión.");
        return "Login"; // redirige a la página de login
    }
}
