package pl.astek.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.argument.StructuredArguments;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.astek.model.Item;
import pl.astek.repo.ItemRepository;

import java.util.UUID;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
@Slf4j
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
    public ResponseEntity<Void> saveItem(@RequestBody Item item, @RequestHeader("correlationId") String correlationId) {
        MDC.put("correlationId1", correlationId);
        log.info("log message {}", StructuredArguments.keyValue("correlationId2","SDAFSLDJKFSLDFJK"));
        log.info("dupa");

        itemRepository.save(item);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
