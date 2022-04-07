package pl.astek.dto.external;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class PricingItem {
    UUID productId;
    BigDecimal price;
}
