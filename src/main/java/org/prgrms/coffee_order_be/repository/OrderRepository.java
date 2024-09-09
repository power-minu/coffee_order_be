package org.prgrms.coffee_order_be.repository;

import org.prgrms.coffee_order_be.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    public List<Order> findAllByEmail(String email);

    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.orderItems WHERE o.email = :email")
    List<Order> findAllByEmailWithOrderItems(@Param("email") String email);
}
