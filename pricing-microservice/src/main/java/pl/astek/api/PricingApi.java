package pl.astek.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.astek.dto.Item;
import pl.astek.repo.ItemRepository;

import java.util.UUID;

@RestController
@RequestMapping("/pricing")
@RequiredArgsConstructor
public class PricingApi {
    private final ItemRepository itemRepository;

    @GetMapping("/{productId}")
    public ResponseEntity<Item> getItem(@PathVariable UUID productId) {
        return ResponseEntity
                .of(itemRepository.findItemByProductId(productId)
                        .map(item -> pl.astek.dto.Item.builder()
                                .productId(item.getProductId())
                                .price(item.getPrice())
                                .build()));
    }

    @PostMapping
    public ResponseEntity<Void> saveItem(@RequestBody pl.astek.model.Item item) {
        itemRepository.save(item);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
