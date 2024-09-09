package org.prgrms.coffee_order_be.model.dto;

import lombok.Data;
import org.prgrms.coffee_order_be.model.OrderItem;

import java.util.UUID;

@Data
public class OrderItemResponseDto {
    private Long orderItemId;
    private UUID productId;
    private int quantity;

    public OrderItemResponseDto(OrderItem orderItem) {
        this.orderItemId = orderItem.getSeq();
        this.productId = orderItem.getProduct().getProductId();
        this.quantity = orderItem.getQuantity();
    }
}
