package org.prgrms.coffee_order_be.repository;

import org.prgrms.coffee_order_be.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

}
