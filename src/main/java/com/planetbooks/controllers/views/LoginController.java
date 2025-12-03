package com.planetbooks.controllers.views;

import com.planetbooks.models.Client;
import com.planetbooks.repositories.ClientRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    private final ClientRepository clientRepo;

    public LoginController(ClientRepository clientRepo) {
        this.clientRepo = clientRepo;
    }

    @GetMapping("/login")
    public String login(
            @RequestParam(value = "denied", required = false) String denied,
            Model model
    ) {
        if ("true".equals(denied)) {
            model.addAttribute("accessDenied", true);
        }
        return "Login";
    }

    @PostMapping("/login")
    public String loginPost(
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session,
            Model model
    ) {
        Client client = clientRepo.findByEmailAndPassword(email, password);

        if (client == null) {
            model.addAttribute("error", "Invalid email or password");
            return "Login";
        }

        // Guardar usuario en sesión
        session.setAttribute("loggedUser", client);

        // Redirecciones según role
        if ("ADMIN".equalsIgnoreCase(client.getRole())) {
            return "redirect:/dash-home";
        } else {
            return "redirect:/catalog";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
