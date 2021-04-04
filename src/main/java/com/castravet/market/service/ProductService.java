package com.castravet.market.service;

import com.castravet.market.dto.ProductDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    List<ProductDto> findAllProducts();

    ProductDto findByProductId(Long productId);

    ResponseEntity<Object> createProduct(ProductDto productDto);

    ResponseEntity<Object> deleteProduct(Long productId);
}
