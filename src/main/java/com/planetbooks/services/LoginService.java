package com.planetbooks.services;

import com.planetbooks.models.Admin;
import com.planetbooks.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    private AdminRepository adminRepository;

    /**
     * Autentica a un administrador por usuario y contraseña.
     * NOTA: En un entorno real, la contraseña debe ser ENCRIPTADA (e.g., BCrypt).
     */
    public Optional<Admin> authenticateAdmin(String username, String password) {
        Optional<Admin> adminOpt = adminRepository.findByUsername(username);

        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();
            // Comparación de contraseña sin encriptar (Temporal)
            if (admin.getPassword().equals(password)) {
                return Optional.of(admin);
            }
        }
        return Optional.empty();
    }
}