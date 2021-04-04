package com.castravet.market.dto.dto_converter;

import com.castravet.market.dto.ProductDto;
import com.castravet.market.model.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductConverter {

    public ProductDto entityToDto(Product product){
        ProductDto dto = new ProductDto();

        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setTitle(product.getTitle());

        return dto;
    }

    public List<ProductDto> entityToDto(List<Product> products){
        return products.stream().map(x -> entityToDto(x)).collect(Collectors.toList());
    }

    public Product dtoToEntity(ProductDto productDto){
        Product product = new Product();

        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setTitle(productDto.getTitle());

        return product;
    }

    public List<Product> dtoToEntity(List<ProductDto> productsDto){
        return productsDto.stream().map(x -> dtoToEntity(x)).collect(Collectors.toList());
    }
}
