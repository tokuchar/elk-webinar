package pl.astek.dto.external;

import lombok.Data;

import java.util.UUID;

@Data
public class InventoryItem {
    UUID productId;
    Long numberOfItems;
}
