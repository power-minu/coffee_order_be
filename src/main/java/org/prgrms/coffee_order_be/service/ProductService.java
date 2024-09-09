package org.prgrms.coffee_order_be.service;

import lombok.RequiredArgsConstructor;
import org.prgrms.coffee_order_be.model.Product;
import org.prgrms.coffee_order_be.model.dto.ProductCreateRequestDto;
import org.prgrms.coffee_order_be.model.dto.ProductListResponseDto;
import org.prgrms.coffee_order_be.model.dto.ProductSingleResponseDto;
import org.prgrms.coffee_order_be.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product addProduct(ProductCreateRequestDto productCreateRequestDto) {
        return productRepository.save(productCreateRequestDto.toEntity());
    }

    public List<ProductListResponseDto> findProductList() {
        List<ProductListResponseDto> productListResponseDtos = new ArrayList<>();

        for (Product p : productRepository.findAll()) {
            productListResponseDtos.add(new ProductListResponseDto(p.getProductId(), p.getProductName()));
        }

        return productListResponseDtos;
    }

    public ProductSingleResponseDto findProduct(UUID uuid) {
        Optional<Product> byId = productRepository.findById(uuid);

        if (byId.isEmpty()) {
            return null;
        }

        return new ProductSingleResponseDto(byId.get().getProductId(), byId.get().getProductName(), byId.get().getCategory().name(), byId.get().getPrice(), byId.get().getDescription());
    }
}
