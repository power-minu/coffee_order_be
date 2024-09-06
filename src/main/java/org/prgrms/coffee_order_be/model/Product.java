package org.prgrms.coffee_order_be.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "products")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
    @Id
    @Column(name = "product_id", columnDefinition = "BINARY(16)")
    @GeneratedValue
    private UUID productId;

    @Column(name = "product_name", length = 20, nullable = false)
    private String productName;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "category", length = 50, nullable = false)
    private Category category;

    @Column(name = "price", nullable = false)
    private Long price;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder
    public Product(String productName, Category category, Long price, String description) {
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.description = description;
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
