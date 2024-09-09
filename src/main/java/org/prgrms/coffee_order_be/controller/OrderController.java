package org.prgrms.coffee_order_be.controller;

import lombok.RequiredArgsConstructor;
import org.prgrms.coffee_order_be.model.Order;
import org.prgrms.coffee_order_be.model.Product;
import org.prgrms.coffee_order_be.model.dto.OrderCreateRequestDto;
import org.prgrms.coffee_order_be.model.dto.OrderResponseDto;
import org.prgrms.coffee_order_be.model.dto.ProductCreateRequestDto;
import org.prgrms.coffee_order_be.service.OrderService;
import org.prgrms.coffee_order_be.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final ProductService productService;
    private final OrderService orderService;

    @PostMapping("")
    public ResponseEntity<OrderResponseDto> orderSave(@RequestBody OrderCreateRequestDto orderCreateRequestDto) {
        OrderResponseDto saved = orderService.addOrder(orderCreateRequestDto);

        if (saved == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saved);
    }
}
