package org.prgrms.coffee_order_be.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ProductSingleResponseDto {
    private UUID productId;
    private String productName;
    private String category;
    private Long price;
    private String description;
}
