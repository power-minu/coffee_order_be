package org.prgrms.coffee_order_be.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ProductListResponseDto {
    private UUID productId;
    private String productName;
}
