package pl.astek.dto;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class ProductDetails {
    String name;
    BigDecimal price;
    boolean isAvailable;
}
