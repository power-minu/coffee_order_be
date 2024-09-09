package org.prgrms.coffee_order_be.model.dto;

import lombok.Data;

@Data
public class OrderUpdateRequestDto {
    private String address;
    private String postcode;
}
