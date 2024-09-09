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
import org.prgrms.coffee_order_be.model.dto.OrderUpdateRequestDto;
import org.prgrms.coffee_order_be.repository.OrderItemRepository;
import org.prgrms.coffee_order_be.repository.OrderRepository;
import org.prgrms.coffee_order_be.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    public OrderResponseDto findOrder(UUID uuid) {
        return new OrderResponseDto(orderRepository.findByOrderIdWithOrderItems(uuid).get());
    }

    public OrderResponseDto modifyOrder(UUID uuid, OrderUpdateRequestDto orderUpdateRequestDto) {
        Optional<Order> find = orderRepository.findByOrderIdWithOrderItems(uuid);

        if (find.isEmpty()) {
            return null;
        }
        Order order = find.get();

        if (orderUpdateRequestDto.getAddress() != null && orderUpdateRequestDto.getAddress() != order.getAddress()) {
            order.setAddress(orderUpdateRequestDto.getAddress());
        }
        if (orderUpdateRequestDto.getPostcode() != null && orderUpdateRequestDto.getPostcode() != order.getPostcode()) {
            order.setPostcode(orderUpdateRequestDto.getPostcode());
        }

        return new OrderResponseDto(orderRepository.save(order));
    }

    @Transactional
    public boolean removeOrder(UUID uuid) {
        Optional<Order> find = orderRepository.findByOrderIdWithOrderItems(uuid);

        if (find.isEmpty()) {
            return false;
        }
        Order order = find.get();
        if (order.getOrderStatus() == OrderStatus.배송시작) {
            return false;
        }

        orderRepository.delete(order);
        return true;
    }

    @Transactional
    public List<OrderResponseDto> deliverOrder() {
        List<Order> orders = new ArrayList<>();
        List<OrderResponseDto> res = new ArrayList<>();

        if (LocalDateTime.now().getHour() >= 14) {
            orders = orderRepository.findOrdersBetweenTimes(
                    LocalDateTime.now().minusDays(1).withHour(14).withMinute(0).withSecond(0).withNano(0),
                    LocalDateTime.now().withHour(14).withMinute(0).withSecond(0).withNano(0)
            );
        } else orders = orderRepository.findOrdersBetweenTimes(
                LocalDateTime.of(2000, 1, 1, 0, 0),
                LocalDateTime.now().minusDays(1).withHour(14).withMinute(0).withSecond(0).withNano(0)
        );

        System.out.println(orders);

        for (Order o : orders) {
            o.setOrderStatus(OrderStatus.배송시작);
            res.add(new OrderResponseDto(orderRepository.save(o)));
        }
        return res;
    }
}
