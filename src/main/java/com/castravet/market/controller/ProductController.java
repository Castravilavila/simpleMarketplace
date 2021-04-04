package com.castravet.market.controller;


import com.castravet.market.dto.ProductDto;
import com.castravet.market.dto.UserDto;
import com.castravet.market.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/products")
    public List<ProductDto> findAll(){
        return productService.findAllProducts();
    }

    @GetMapping("/product/{productId}")
    public ProductDto findUserById(@PathVariable Long productId){
        return productService.findByProductId(productId);
    }

    @PostMapping("/product")
    public ResponseEntity<Object> createUser(@RequestBody ProductDto productDto){
        return productService.createProduct(productDto);
    }

//    @PostMapping("user/update")
//    public ResponseEntity<Object> updateUser(@RequestBody UserDto userDto){
//
//    }

    @DeleteMapping("/product/delete/{productId}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long productId){
        return productService.deleteProduct(productId);
    }
}
