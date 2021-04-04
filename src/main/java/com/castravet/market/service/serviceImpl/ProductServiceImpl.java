package com.castravet.market.service.serviceImpl;

import com.castravet.market.dto.ProductDto;
import com.castravet.market.dto.dto_converter.ProductConverter;
import com.castravet.market.model.Product;
import com.castravet.market.repository.ProductRepository;
import com.castravet.market.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductConverter productConverter;


    @Override
    public List<ProductDto> findAllProducts() {
        List<Product> allProducts = productRepository.findAll();
        return productConverter.entityToDto(allProducts);
    }

    @Override
    public ProductDto findByProductId(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        return product.map(productConverter::entityToDto).orElse(null);
    }

    @Override
    public ResponseEntity<Object> createProduct(ProductDto productDto) {
        Product product = productConverter.dtoToEntity(productDto);
        productRepository.save(product);

        Product sameProductButFromDb = productRepository
                .findByTitleAndDescription(product.getTitle(),product.getDescription());

        if (sameProductButFromDb==null){
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> deleteProduct(Long productId) {
        Product product = productRepository.getOne(productId);
        if (product==null){
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        productRepository.deleteById(productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
