package com.castravet.market.service;

import com.castravet.market.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

public interface ProductService {
    Page<ProductDto> findAllProducts(PageRequest pageRequest);

    ProductDto findByProductId(Long productId);

    ResponseEntity<Object> createProduct(ProductDto productDto);

    ResponseEntity<Object> deleteProduct(Long productId);

    ResponseEntity<Object> addProductToUser(Long productId, Long id);

    Page<ProductDto> getAllProductsByCurrentUser(Long id, PageRequest pageRequest);

    ResponseEntity<Object> removeProductFromUser(Long productId, Long userId);

    ResponseEntity<Object> modifyProduct(ProductDto newProduct, Long productId);

    ResponseEntity<Object> likeProduct(Long productId, Long userId);

    ResponseEntity<Object> dislikeProduct(Long productId, Long userId);
}
