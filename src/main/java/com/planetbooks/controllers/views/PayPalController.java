package com.planetbooks.controllers.views;

import com.paypal.orders.*;
import com.paypal.core.PayPalHttpClient;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/paypal")
public class PayPalController {

    private final PayPalHttpClient payPalClient;

    public PayPalController(PayPalHttpClient payPalClient) {
        this.payPalClient = payPalClient;
    }

    @PostMapping("/create-order")
    public String createOrder(@RequestParam double total) throws IOException {

        OrderRequest order = new OrderRequest();
        order.checkoutPaymentIntent("CAPTURE");

        ApplicationContext applicationContext = new ApplicationContext()
                .brandName("Planet Books")
                .landingPage("LOGIN");

        PurchaseUnitRequest purchaseUnit = new PurchaseUnitRequest()
                .amountWithBreakdown(new AmountWithBreakdown().currencyCode("USD")
                        .value(String.format("%.2f", total)));

        order.applicationContext(applicationContext)
                .purchaseUnits(List.of(purchaseUnit));

        OrdersCreateRequest request = new OrdersCreateRequest()
                .requestBody(order);

        com.paypal.http.HttpResponse<Order> response = payPalClient.execute(request);

        return response.result().id();
    }

    @PostMapping("/capture-order")
    public Order captureOrder(@RequestParam String orderId) throws IOException {
        OrdersCaptureRequest request = new OrdersCaptureRequest(orderId);
        return payPalClient.execute(request).result();
    }
}
