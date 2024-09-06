package org.prgrms.coffee_order_be.controller;

import lombok.RequiredArgsConstructor;
import org.prgrms.coffee_order_be.model.Product;
import org.prgrms.coffee_order_be.model.dto.ProductCreateRequestDto;
import org.prgrms.coffee_order_be.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductRepository productRepository;

    @PostMapping("")
    public ResponseEntity<Product> productSave(@RequestBody ProductCreateRequestDto productCreateRequestDto) {
        Product newProduct = productCreateRequestDto.toEntity();
        Product saved = productRepository.save(newProduct);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saved);
    }
}
