package com.planetbooks.config;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PayPalConfig {

    @Bean
    public PayPalHttpClient payPalClient() {
        PayPalEnvironment environment = new PayPalEnvironment.Sandbox(
                "AUAQLoyeT0ZMqOCVye8ioYOEJ_oxYunFuEiAT3rTOHVnKeiSXH56YzXRyLUIxJPHmMZoih4KWz9UgUzb",
                "EAMuPghslcLRYRNkIl16llkWlNe24b39My8giBHMXRFylyo-ZuqF65W4Q0abZk-F4EsuUR5-3dOGZ9OW"
        );

        return new PayPalHttpClient(environment);
    }
}
