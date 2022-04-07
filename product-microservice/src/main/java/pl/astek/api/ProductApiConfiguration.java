package pl.astek.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import pl.astek.repo.ProductRepository;

@Configuration
public class ProductApiConfiguration {
    @Value("${endpoint.pricing}")
    private String pricingEndpoint;
    @Value("${endpoint.inventory}")
    private String inventoryEndpoint;

    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    ProductApi productApi(ProductRepository productRepository, RestTemplate restTemplate){
        return new ProductApi(productRepository, restTemplate, pricingEndpoint, inventoryEndpoint);
    }
}
