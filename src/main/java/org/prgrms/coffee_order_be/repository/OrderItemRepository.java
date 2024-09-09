package org.prgrms.coffee_order_be.repository;

import org.prgrms.coffee_order_be.model.Order;
import org.prgrms.coffee_order_be.model.OrderItem;
import org.prgrms.coffee_order_be.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {
    public List<OrderItem> findAllByOrder(Order order);
}