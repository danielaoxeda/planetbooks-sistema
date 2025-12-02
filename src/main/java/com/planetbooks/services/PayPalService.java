package com.planetbooks.services;

import org.json.JSONObject;
import org.json.JSONArray;
import java.net.http.HttpResponse;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.Base64;

public class PayPalService {

    private static final String CLIENT_ID = "AUAQLoyeT0ZMqOCVye8ioYOEJ_oxYunFuEiAT3rTOHVnKeiSXH56YzXRyLUIxJPHmMZoih4KWz9UgUzb";
    private static final String SECRET = "EAMuPghslcLRYRNkIl16llkWlNe24b39My8giBHMXRFylyo-ZuqF65W4Q0abZk-F4EsuUR5-3dOGZ9OW";
    private static final String PAYPAL_API = "https://api-m.sandbox.paypal.com";

    private static String getAccessToken() throws Exception {
        String auth = CLIENT_ID + ":" + SECRET;
        String encoded = Base64.getEncoder().encodeToString(auth.getBytes());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(PAYPAL_API + "/v1/oauth2/token"))
                .header("Authorization", "Basic " + encoded)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString("grant_type=client_credentials"))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JSONObject json = new JSONObject(response.body());
        return json.getString("access_token");
    }

    public static String createOrder(String amount) throws Exception {
        String token = getAccessToken();

        JSONObject order = new JSONObject();
        order.put("intent", "CAPTURE");

        JSONObject purchaseUnit = new JSONObject();
        purchaseUnit.put("amount", new JSONObject()
                .put("currency_code", "USD")
                .put("value", amount)
        );

        order.put("purchase_units", new JSONArray().put(purchaseUnit));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(PAYPAL_API + "/v2/checkout/orders"))
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(order.toString()))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JSONObject json = new JSONObject(response.body());
        return json.getString("id");
    }

    public static boolean captureOrder(String orderId) throws Exception {
        String token = getAccessToken();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(PAYPAL_API + "/v2/checkout/orders/" + orderId + "/capture"))
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{}"))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JSONObject json = new JSONObject(response.body());
        return json.getString("status").equals("COMPLETED");
    }
}
