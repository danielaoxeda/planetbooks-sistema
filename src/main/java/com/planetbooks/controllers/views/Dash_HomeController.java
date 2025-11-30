package com.planetbooks.controllers.views;

import com.planetbooks.models.Client;
import com.planetbooks.models.LatestSold;
import com.planetbooks.models.Venta;
import com.planetbooks.repositories.ClientRepository;
import com.planetbooks.repositories.VentaRepository;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class Dash_HomeController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private VentaRepository ventaRepository;

    @GetMapping({"/dash-home"})
    public String catalog(HttpServletRequest request, Model model) {

        model.addAttribute("currentPath", request.getRequestURI());

        try {
            // Total de ventas mensuales
            BigDecimal monthlySales = ventaRepository.getMonthlySales();
            model.addAttribute("monthlySales", monthlySales != null ? monthlySales : BigDecimal.ZERO);

            // Total de libros vendidos
            Integer totalBooksSold = ventaRepository.getTotalBooksSold();
            model.addAttribute("totalBooksSold", totalBooksSold != null ? totalBooksSold : 0);

            // Nuevos usuarios (clientes activos)
            List<Client> newClients = clientRepository.findByActiveTrue();
            model.addAttribute("newClientsCount", newClients.size());
            model.addAttribute("newClients", newClients);

            // Últimas ventas (5 últimas)
            List<Venta> recentSales = ventaRepository.findTop5ByOrderBySaleDateDesc();
            model.addAttribute("recentSales", recentSales);

            // Top 5 libros más vendidos
            List<Object[]> topBooks = ventaRepository.getTopSellingBooks();
            model.addAttribute("topBooks", topBooks);

            // System failures (estático por ahora)
            model.addAttribute("systemFailures", 3);

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("monthlySales", BigDecimal.ZERO);
            model.addAttribute("totalBooksSold", 0);
            model.addAttribute("newClientsCount", 0);
            model.addAttribute("newClients", null);
            model.addAttribute("recentSales", null);
            model.addAttribute("topBooks", null);
            model.addAttribute("systemFailures", 0);
        }

        // Últimos 5 clientes registrados
        List<Client> latestClients = clientRepository.findLatest5ActiveClients();
        model.addAttribute("latestClients", latestClients);

        // 🔥 Últimos 5 libros vendidos (para tabla dinámica)
        List<LatestSold> latestBooks = ventaRepository.findLatestBooksSold();
        model.addAttribute("latestBooks", latestBooks);

        // ✅ Clientes por país para gráfico de pastel
        List<Object[]> countryData = clientRepository.getClientsByCountry();
        List<String> countryLabels = new ArrayList<>();
        List<Long> countryTotals = new ArrayList<>();

        for (Object[] row : countryData) {
            countryLabels.add((String) row[0]);
            countryTotals.add(((Number) row[1]).longValue());
        }

        model.addAttribute("countryLabels", countryLabels);
        model.addAttribute("countryData", countryTotals);

        return "admin/dash-home";
    }
}
