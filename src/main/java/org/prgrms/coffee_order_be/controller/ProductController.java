package org.prgrms.coffee_order_be.controller;

import lombok.RequiredArgsConstructor;
import org.prgrms.coffee_order_be.model.Product;
import org.prgrms.coffee_order_be.model.dto.ProductCreateRequestDto;
import org.prgrms.coffee_order_be.model.dto.ProductListResponseDto;
import org.prgrms.coffee_order_be.model.dto.ProductSingleResponseDto;
import org.prgrms.coffee_order_be.repository.ProductRepository;
import org.prgrms.coffee_order_be.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductRepository productRepository;
    private final ProductService productService;

    @PostMapping("")
    public ResponseEntity<Product> productSave(@RequestBody ProductCreateRequestDto productCreateRequestDto) {
        Product saved = productService.addProduct(productCreateRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saved);
    }

    @GetMapping("")
    public ResponseEntity<List<ProductListResponseDto>> productList() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.findProductList());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ProductSingleResponseDto> productDetails(@PathVariable UUID uuid) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.findProduct(uuid));
    }

    @PutMapping("{uuid}")
    public ResponseEntity<ProductSingleResponseDto> productModify(@PathVariable UUID uuid, @RequestBody ProductCreateRequestDto productCreateRequestDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.modifyProduct(uuid, productCreateRequestDto));
    }
}
