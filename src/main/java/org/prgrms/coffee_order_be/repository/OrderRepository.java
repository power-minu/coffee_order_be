package org.prgrms.coffee_order_be.repository;

import org.prgrms.coffee_order_be.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    public List<Order> findAllByEmail(String email);

    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.orderItems WHERE o.email = :email")
    List<Order> findAllByEmailWithOrderItems(@Param("email") String email);

    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.orderItems WHERE o.orderId = :orderId")
    Optional<Order> findByOrderIdWithOrderItems(@Param("orderId") UUID orderId);

    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.orderItems WHERE o.createdAt >= :startTime AND o.createdAt <= :endTime")
    List<Order> findOrdersBetweenTimes(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
}
