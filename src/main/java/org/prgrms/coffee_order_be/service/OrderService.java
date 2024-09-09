package org.prgrms.coffee_order_be.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.prgrms.coffee_order_be.model.Order;
import org.prgrms.coffee_order_be.model.OrderItem;
import org.prgrms.coffee_order_be.model.OrderStatus;
import org.prgrms.coffee_order_be.model.Product;
import org.prgrms.coffee_order_be.model.dto.OrderCreateRequestDto;
import org.prgrms.coffee_order_be.model.dto.OrderResponseDto;
import org.prgrms.coffee_order_be.repository.OrderItemRepository;
import org.prgrms.coffee_order_be.repository.OrderRepository;
import org.prgrms.coffee_order_be.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final ProductRepository productRepository;

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    public OrderResponseDto addOrder(OrderCreateRequestDto orderCreateRequestDto) {

        for (UUID u : orderCreateRequestDto.getOrderItems().keySet()) {
            if (productRepository.findById(u).isEmpty()) {
                return null;
            }
        }

        Order order = new Order(orderCreateRequestDto.getEmail(), orderCreateRequestDto.getAddress(), orderCreateRequestDto.getPostcode(), OrderStatus.주문완료);
        orderRepository.save(order);
        for (UUID u : orderCreateRequestDto.getOrderItems().keySet()) {
            Product product = productRepository.findById(u).get();

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .category(String.valueOf(product.getCategory()))
                    .price(product.getPrice())
                    .quantity(orderCreateRequestDto.getOrderItems().get(u))
                    .build();

            orderItemRepository.save(orderItem);
            order.addOrderItem(orderItem);
        }
        Order saved = orderRepository.save(order);
        Hibernate.initialize(saved.getOrderItems());

        return new OrderResponseDto(saved);
    }

    public List<OrderResponseDto> findOrderList(String email) {
        List<OrderResponseDto> res = new ArrayList<>();

        List<Order> allByEmail = orderRepository.findAllByEmailWithOrderItems(email);
        for (Order o : allByEmail) {
            res.add(new OrderResponseDto(o));
        }

        return res;
    }
}
