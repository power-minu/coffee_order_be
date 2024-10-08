package org.prgrms.coffee_order_be.controller;

import lombok.RequiredArgsConstructor;
import org.prgrms.coffee_order_be.model.dto.OrderCreateRequestDto;
import org.prgrms.coffee_order_be.model.dto.OrderResponseDto;
import org.prgrms.coffee_order_be.model.dto.OrderUpdateRequestDto;
import org.prgrms.coffee_order_be.service.OrderService;
import org.prgrms.coffee_order_be.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @GetMapping("")
    public ResponseEntity<List<OrderResponseDto>> orderList(@RequestParam String email) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderService.findOrderList(email));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<OrderResponseDto> orderDetails(@PathVariable UUID uuid) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderService.findOrder(uuid));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<OrderResponseDto> orderModify(@PathVariable UUID uuid, @RequestBody OrderUpdateRequestDto orderUpdateRequestDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderService.modifyOrder(uuid, orderUpdateRequestDto));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<String> orderModify(@PathVariable UUID uuid) {
        if (orderService.removeOrder(uuid) == false) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("없는 주문이거나, 배송이 시작되어 취소할 수 없는 주문입니다.");
        } else return ResponseEntity
                .status(HttpStatus.OK)
                .body("삭제되었습니다.");
    }

    @PutMapping("")
    public ResponseEntity<List<OrderResponseDto>> orderDeliver() {
        List<OrderResponseDto> orderResponseDtos = orderService.deliverOrder();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderResponseDtos);
    }
}
