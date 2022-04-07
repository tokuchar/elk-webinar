package pl.astek;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@SpringBootApplication
public class PricingMicroservice {
    public static void main(String[] args) {
        SpringApplication.run(PricingMicroservice.class, args);
    }
}
