package pl.astek.dto;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Value
public class Item {
    UUID productId;
    BigDecimal price;
}
