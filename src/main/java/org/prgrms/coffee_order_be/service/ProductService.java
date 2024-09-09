package org.prgrms.coffee_order_be.service;

import lombok.RequiredArgsConstructor;
import org.prgrms.coffee_order_be.model.Category;
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

    public ProductSingleResponseDto modifyProduct(UUID uuid, ProductCreateRequestDto productCreateRequestDto) {
        Optional<Product> byId = productRepository.findById(uuid);
        if (byId.isEmpty()) {
            return null;
        }

        Product product = byId.get();

        if (productCreateRequestDto.getProductName() != null && productCreateRequestDto.getProductName() != product.getProductName()) {
            product.setProductName(productCreateRequestDto.getProductName());
        }
        if (productCreateRequestDto.getCategory() != null && productCreateRequestDto.getCategory() != product.getCategory().name()) {
            product.setCategory(Category.valueOf(productCreateRequestDto.getCategory()));
        }
        if (productCreateRequestDto.getPrice() != null && productCreateRequestDto.getPrice() != product.getPrice()) {
            product.setPrice(productCreateRequestDto.getPrice());
        }
        if (productCreateRequestDto.getDescription() != null && productCreateRequestDto.getDescription() != product.getDescription()) {
            product.setDescription(productCreateRequestDto.getDescription());
        }
        Product saved = productRepository.save(product);

        return new ProductSingleResponseDto(saved.getProductId(), saved.getProductName(), saved.getCategory().name(), saved.getPrice(), saved.getDescription());
    }

    public boolean removeProduct(UUID uuid) {
        Optional<Product> byId = productRepository.findById(uuid);
        if (byId.isEmpty()) {
            return false;
        }

        productRepository.delete(byId.get());
        return true;
    }
}
