package org.prgrms.coffee_order_be.model.dto;

import lombok.Data;
import org.prgrms.coffee_order_be.model.Category;
import org.prgrms.coffee_order_be.model.Product;

import java.util.UUID;

@Data
public class ProductCreateRequestDto {
    private String productName;
    private String category;
    private Long price;
    private String description;

    public Product toEntity() {
        return Product.builder()
                .productName(this.productName)
                .category(Category.valueOf(this.category.toUpperCase()))  // Category enum 변환
                .price(this.price)
                .description(this.description)
                .build();
    }
}