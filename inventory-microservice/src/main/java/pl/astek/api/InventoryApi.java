package pl.astek.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.astek.model.Item;
import pl.astek.repo.ItemRepository;

import java.util.UUID;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryApi {
    private final ItemRepository itemRepository;

    @GetMapping("/{productId}")
    public ResponseEntity<pl.astek.dto.Item> getItem(@PathVariable UUID productId) {
        return ResponseEntity
                .of(itemRepository.findItemByProductId(productId)
                        .map(item -> pl.astek.dto.Item.builder()
                                .productId(item.getProductId())
                                .numberOfItems(item.getNumberOfItems())
                                .build()));
    }

    @PostMapping
    public ResponseEntity<Void> saveItem(@RequestBody Item item) {
        itemRepository.save(item);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
