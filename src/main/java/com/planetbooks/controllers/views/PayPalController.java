package com.planetbooks.controllers.views;

import com.paypal.orders.*;
import com.planetbooks.models.Inventory;
import com.planetbooks.services.EmailService;
import com.planetbooks.services.InventoryService;
import com.planetbooks.services.PayPalService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/paypal")
public class PayPalController {

    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder(@RequestBody Map<String, Object> request) {
        try {
            String amount = request.get("amount").toString();
            String orderId = PayPalService.createOrder(amount);

            return ResponseEntity.ok(Map.of("id", orderId));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    @Autowired
    private EmailService emailService;

    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/capture-order")
    public ResponseEntity<?> capture(@RequestBody Map<String, Object> body, HttpSession session) {

        try {
            String orderId = body.get("orderId").toString();
            boolean ok = PayPalService.captureOrder(orderId);

            if (ok) {
                // email del usuario
                String email = (String) session.getAttribute("userEmail");

                // obtener carrito
                List<Map<String, Object>> cart = (List<Map<String, Object>>) session.getAttribute("cartItems");

                List<Inventory> purchasedBooks = new ArrayList<>();

                for (Map<String, Object> item : cart) {
                    int id = Integer.parseInt(item.get("id").toString());
                    inventoryService.findById(id).ifPresent(purchasedBooks::add);
                }

                // enviar correo
                emailService.sendBooks(email, purchasedBooks);

                return ResponseEntity.ok(Map.of("status", "COMPLETED"));
            }

            return ResponseEntity.ok(Map.of("status", "FAILED"));

        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

}