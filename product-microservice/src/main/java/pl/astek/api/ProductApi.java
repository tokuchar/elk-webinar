package pl.astek.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import pl.astek.dto.Product;
import pl.astek.dto.ProductDetails;
import pl.astek.dto.external.InventoryItem;
import pl.astek.dto.external.PricingItem;
import pl.astek.repo.ProductRepository;

import java.util.UUID;

@Slf4j
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductApi {
    private final ProductRepository productRepository;
    private final RestTemplate restTemplate;
    private final String pricingEndpoint;
    private final String inventoryEndpoint;

    @GetMapping("/{productId}/details")
    public ResponseEntity<ProductDetails> getProductDetails(@PathVariable UUID productId, @RequestHeader("correlationId") String correlationId) {
        MDC.put("correlationId1", correlationId);
        log.info("get product details" + productId + " processing");


        ResponseEntity<PricingItem> pricingItem = restTemplate.exchange(pricingEndpoint + productId,
                HttpMethod.GET, createEntityWithCorrelationId(correlationId), PricingItem.class);
        ResponseEntity<InventoryItem> inventoryItem = restTemplate.exchange(inventoryEndpoint + productId,
                HttpMethod.GET, createEntityWithCorrelationId(correlationId), InventoryItem.class);
        pl.astek.model.Product product = productRepository.getById(productId);

        ProductDetails productDetails = ProductDetails.builder()
                .name(product.getName())
                .price(pricingItem.getBody().getPrice())
                .isAvailable(inventoryItem.getBody().getNumberOfItems() > 0)
                .build();

        return ResponseEntity
                .ok(productDetails);
    }

    private HttpEntity<Void> createEntityWithCorrelationId(String correlationId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("correlationId", correlationId);
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        return new HttpEntity<>(headers);
    }

    @PostMapping
    public ResponseEntity<UUID> saveProduct(@RequestBody Product product) {
        pl.astek.model.Product entity = productRepository.save(pl.astek.model.Product.builder()
                .name(product.getName())
                .build());
        return new ResponseEntity<>(entity.getId(), HttpStatus.CREATED);
    }
}

