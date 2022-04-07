package pl.astek.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import pl.astek.dto.Product;
import pl.astek.dto.ProductDetails;
import pl.astek.dto.external.InventoryItem;
import pl.astek.dto.external.PricingItem;
import pl.astek.repo.ProductRepository;

import java.util.UUID;

@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductApi {
    private final ProductRepository productRepository;
    private final RestTemplate restTemplate;
    private final String pricingEndpoint;
    private final String inventoryEndpoint;

    @GetMapping("/{productId}/details")
    public ResponseEntity<ProductDetails> getProductDetails(@PathVariable UUID productId) {

        ResponseEntity<PricingItem> pricingItem = restTemplate.getForEntity(pricingEndpoint + productId, PricingItem.class);
        ResponseEntity<InventoryItem> inventoryItem = restTemplate.getForEntity(inventoryEndpoint + productId, InventoryItem.class);
        pl.astek.model.Product product = productRepository.getById(productId);

        ProductDetails productDetails = ProductDetails.builder()
                .name(product.getName())
                .price(pricingItem.getBody().getPrice())
                .isAvailable(inventoryItem.getBody().getNumberOfItems() > 0)
                .build();

        return ResponseEntity
                .ok(productDetails);
    }

    @PostMapping
    public ResponseEntity<UUID> saveProduct(@RequestBody Product product) {
        pl.astek.model.Product entity = productRepository.save(pl.astek.model.Product.builder()
                .name(product.getName())
                .build());
        return new ResponseEntity<>(entity.getId(), HttpStatus.CREATED);
    }
}

