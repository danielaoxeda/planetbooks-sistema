package com.planetbooks.controllers.views;

import com.planetbooks.models.Client;
import com.planetbooks.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;

@Controller
public class RegisterController {

    @Autowired
    private ClientRepository clientRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("client", new Client());
        return "Register";
    }

    @PostMapping("/register")
    public String registerClient(
            @Valid @ModelAttribute("client") Client client,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            return "Register";
        }

        if (clientRepository.findByActiveTrue().stream()
                .anyMatch(c -> c.getUser().equals(client.getUser()))) {
            model.addAttribute("errorUser", "El nombre de usuario ya existe");
            return "Register";
        }

        if (clientRepository.findByActiveTrue().stream()
                .anyMatch(c -> c.getEmail().equals(client.getEmail()))) {
            model.addAttribute("errorEmail", "El correo ya está registrado");
            return "Register";
        }

        client.setPassword(passwordEncoder.encode(client.getPassword()));

        client.setRole("CLIENT");
        client.setRegistration_date(LocalDate.now());
        client.setActive(true);
        client.setPurchases(0);
        client.setSessions(0);

        clientRepository.save(client);

        model.addAttribute("success", "Registro exitoso. Ya puedes iniciar sesión.");
        return "Login";
    }
}
