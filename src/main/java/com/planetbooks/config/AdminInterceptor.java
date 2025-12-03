package com.planetbooks.config;

import com.planetbooks.models.Client;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AdminInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        HttpSession session = request.getSession(false);

        // Si no hay sesión → login
        if (session == null) {
            response.sendRedirect("/login?denied=true");
            return false;
        }

        Client user = (Client) session.getAttribute("loggedUser");

        // Si no hay user o no es admin → login
        if (user == null || !"ADMIN".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect("/login?denied=true");
            return false;
        }

        return true; // permitir acceso
    }
}
