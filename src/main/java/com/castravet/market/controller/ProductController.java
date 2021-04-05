package com.castravet.market.controller;


import com.castravet.market.dto.ProductDto;
import com.castravet.market.model.User;
import com.castravet.market.repository.ProductRepository;
import com.castravet.market.security.CurrentUser;
import com.castravet.market.security.UserPrincipal;
import com.castravet.market.service.ProductService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
    @ApiOperation(value="find All Products",
            notes = "Returns a page Object of ProductDto, " +
                    "it has 2 optional params: page and sortBy, for chosing " +
                    "the page number and the sorting field",
            response =Page.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "SUCCES",response = Page.class)})
    public Page<ProductDto> findAll(@ApiParam(value="page number you want to retrieve")
                                    @RequestParam Optional<Integer> page,
                                    @ApiParam(value="sorting direction of field you choose")
                                    @RequestParam Optional<String> sortBy){
        return productService.findAllProducts(PageRequest.of(page.orElse(0),
                10, Sort.Direction.ASC, sortBy.orElse("id")));
    }

    @GetMapping("/product/{productId}")
    @ApiOperation(value="find Product by Id",
            response =ProductDto.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "SUCCES",response = ProductDto.class)})
    public ProductDto findProductById(@ApiParam(value="Id of product you want to retrieve",required = true)
                                      @PathVariable Long productId){
        return productService.findByProductId(productId);
    }


    @PostMapping("/product")
    @ApiOperation(value="Create a product",
            notes = "Creates and saves a product to database " +
                    "by providing title, description and price",
            response =ResponseEntity.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "SUCCES",response = ResponseEntity.class)})
    public ResponseEntity<Object> createProduct(@ApiParam(value="Body Of ProductDto containing title, price, description",required = true)
                                                @RequestBody ProductDto productDto){
        return productService.createProduct(productDto);
    }

    @PostMapping("/product/like/{productId}")
    @ApiOperation(value="Like a product",
            notes = "Increments 'liked' field present in Product entity and " +
                    "adds product id to 'likedProducts' set present in User entity. " +
                    "If already clicked then Product is removed from the set " +
                    "and 'liked' field is decremented ",
            response =ResponseEntity.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "SUCCES",response = ResponseEntity.class)})
    public ResponseEntity<Object> likeProduct(@ApiParam(value="Id of product",required = true)
                                              @PathVariable Long productId,
                                              @ApiParam(value="Current logged user",required = true)
                                              @CurrentUser UserPrincipal currentUser){
        return productService.likeProduct(productId,currentUser.getId());
    }


    @PostMapping("/product/dislike/{productId}")
    @ApiOperation(value="Dislike a product",
            notes = "Increments 'disliked' field present in Product entity and " +
                    "adds product id to 'unlikedProducts' set present in User entity. " +
                    "If already clicked then Product is removed from the set " +
                    "and 'disliked' field is decremented ",
            response =ResponseEntity.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "SUCCES",response = ResponseEntity.class)})
    public ResponseEntity<Object> dislikeProduct(@ApiParam(value="Id of product",required = true)
                                                 @PathVariable Long productId,
                                                 @ApiParam(value="Current logged user",required = true)
                                                 @CurrentUser UserPrincipal currentUser){
        return productService.dislikeProduct(productId,currentUser.getId());
    }

    @PostMapping("product/add/{productId}")
    @ApiOperation(value="Add product to Current User",
            notes = "Find product by Id and add to 'products' set from " +
                    "User entity ",
            response =ResponseEntity.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "SUCCES",response = ResponseEntity.class)})
    public ResponseEntity<Object> addProductToUser(@ApiParam(value="Id of product",required = true)
                                                   @PathVariable Long productId,
                                                   @ApiParam(value="Current logged user",required = true)
                                                   @CurrentUser  UserPrincipal currentUser){
        return productService.addProductToUser(productId,currentUser.getId());
    }

    @GetMapping("/products/byUser")
    @ApiOperation(value="Get all products added by current user",
            notes = "Return a paged list of ProductDtos, api has 2 optional " +
                    "parameters :page and sortBy for choosing page number and sorting field",
            response =Page.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "SUCCES",response = Page.class)})
    public Page<ProductDto> getAllProductsByCurrentUser(@ApiParam(value="Current logged user",required = true)
                                                        @CurrentUser UserPrincipal currentUser,
                                                        @ApiParam(value="Optional page number")
                                                        @RequestParam Optional<Integer> page,
                                                        @ApiParam(value="Optional sortyBy any field String")
                                                        @RequestParam Optional<String> sortBy){

        return productService.getAllProductsByCurrentUser(currentUser.getId(), PageRequest.of(page.orElse(0),
                10, Sort.Direction.ASC, sortBy.orElse("id")));
    }

    @DeleteMapping("product/removeFromUser/{productId}")
    @ApiOperation(value="Remove Product From Current User",
            notes = "Remove Product by id from current user",
            response =ResponseEntity.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "SUCCES",response = ResponseEntity.class)})
    public ResponseEntity<Object> removeFromUser(@ApiParam(value="Id of product",required = true)
                                                 @PathVariable Long productId,
                                                 @ApiParam(value="Current logged user",required = true)
                                                 @CurrentUser UserPrincipal currentUser){
        return productService.removeProductFromUser(productId,currentUser.getId());
    }

    @PutMapping("product/update/{productId}")
    @ApiOperation(value="Update product fields",
            notes = "Get product by Id and change fields with " +
                    "the fields passed in the body of ProductDto " +
                    "then save to tha database with the same Id",
            response =ResponseEntity.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "SUCCES",response = ResponseEntity.class)})
    public ResponseEntity<Object> modifyProduct(@ApiParam(value="Edited ProductDto",required = true)
                                                @RequestBody ProductDto newProduct,
                                                @ApiParam(value="Id of product",required = true)
                                                @PathVariable Long productId){
        return productService.modifyProduct(newProduct,productId);
    }

    @DeleteMapping("/product/delete/{productId}")
    @ApiOperation(value="Delete product by ID from DB",
            response =ResponseEntity.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "SUCCES",response = ResponseEntity.class)})
    public ResponseEntity<Object> deleteProduct(@ApiParam(value="Id of product",required = true)
                                                @PathVariable Long productId){
        return productService.deleteProduct(productId);
    }


}
