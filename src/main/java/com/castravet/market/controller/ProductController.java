package com.castravet.market.controller;


import com.castravet.market.dto.ProductDto;
import com.castravet.market.repository.ProductRepository;
import com.castravet.market.security.CurrentUser;
import com.castravet.market.security.UserPrincipal;
import com.castravet.market.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductRepository productRepository;

    @GetMapping("/products")
    public Page<ProductDto> findAll(@RequestParam Optional<Integer> page,
                                    @RequestParam Optional<String> sortBy){
        return productService.findAllProducts(PageRequest.of(page.orElse(0),
                10, Sort.Direction.ASC, sortBy.orElse("id")));
    }

    @GetMapping("/product/{productId}")
    public ProductDto findUserById(@PathVariable Long productId){
        return productService.findByProductId(productId);
    }

    @PostMapping("/product")
    public ResponseEntity<Object> createUser(@RequestBody ProductDto productDto){
        return productService.createProduct(productDto);
    }

    @PostMapping("product/add/{productId}")
    public ResponseEntity<Object> addProductToUser(@PathVariable Long productId, @CurrentUser UserPrincipal currentUser){
        return productService.addProductToUser(productId,currentUser.getId());
    }

    @GetMapping("/products/byUser")
    public Page<ProductDto> getAllProductsByCurrentUser(@CurrentUser UserPrincipal currentUser,
                                                     @RequestParam Optional<Integer> page,
                                                     @RequestParam Optional<String> sortBy){

        return productService.getAllProductsByCurrentUser(currentUser.getId(), PageRequest.of(page.orElse(0),
                10, Sort.Direction.ASC, sortBy.orElse("id")));
    }

    @DeleteMapping("product/removeFromUser/{productId}")
    public ResponseEntity<Object> removeFromUser(@PathVariable Long productId, @CurrentUser UserPrincipal currentUser){
        return productService.removeProductFromUser(productId,currentUser.getId());
    }

    @PutMapping("product/update/{productId}")
    public ResponseEntity<Object> modifyProduct(@RequestBody ProductDto newProduct, @PathVariable Long productId){
        return productService.modifyProduct(newProduct,productId);
    }

    @DeleteMapping("/product/delete/{productId}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long productId){
        return productService.deleteProduct(productId);
    }
}
