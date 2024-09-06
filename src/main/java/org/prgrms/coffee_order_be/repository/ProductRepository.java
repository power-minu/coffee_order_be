package org.prgrms.coffee_order_be.repository;

import org.prgrms.coffee_order_be.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

}
