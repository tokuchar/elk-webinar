package pl.astek.dto;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Builder
@Value
public class Item {
    UUID productId;
    Long numberOfItems;
}
