package org.prgrms.coffee_order_be.model.dto;

import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
public class OrderCreateRequestDto {
    private Map<UUID, Integer> orderItems;
    private String email;
    private String address;
    private String postcode;
}
