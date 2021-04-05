package com.castravet.market.service.serviceImpl;

import com.castravet.market.dto.ProductDto;
import com.castravet.market.model.Product;
import com.castravet.market.model.User;
import com.castravet.market.repository.ProductRepository;
import com.castravet.market.repository.UserRepository;
import com.castravet.market.dto.dto_converter.ProductConverter;
import com.castravet.market.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.lang.Math.toIntExact;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ProductConverter productConverter;


    @Override
    public Page<ProductDto> findAllProducts(PageRequest pageRequest) {
        List<Product> allProducts = productRepository.findAll();
        List<ProductDto> allProductsButDto = productConverter.entityToDto(allProducts);

        return convertProductListToPage(allProductsButDto,pageRequest);
    }

    @Override
    public ProductDto findByProductId(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        return product.map(productConverter::entityToDto).orElse(null);
    }

    @Override
    public Page<ProductDto> getAllProductsByCurrentUser(Long id, PageRequest pageRequest) {
        List<Product> allProducts = productRepository.findByUsers_Id(id);
        List<ProductDto> allProductsButDto = productConverter.entityToDto(allProducts);

        return convertProductListToPage(allProductsButDto,pageRequest);
    }

    @Override
    public ResponseEntity<Object> modifyProduct(ProductDto newProduct, Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if(product.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        product.get().setTitle(newProduct.getTitle());
        product.get().setPrice(newProduct.getPrice());
        product.get().setDescription(newProduct.getDescription());
        productRepository.save(product.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> removeProductFromUser(Long productId, Long userId) {
        Optional<User> user = userRepository.findById(userId);
        Product product = productRepository.getOne(productId);

        if (product!=null){
            user.get().getProducts().remove(product);
            userRepository.save(user.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
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

    @Override
    public ResponseEntity<Object> addProductToUser(Long productId, Long userId) {
        Optional<User> user = userRepository.findById(userId);
        Product product = productRepository.getOne(productId);

        if (user.isEmpty() || isProductPresentInSet(product, user.get().getProducts())){
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        user.get().getProducts().add(product);
        userRepository.save(user.get());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> likeProduct(Long productId, Long userId) {
        Optional<Product> product = productRepository.findById(productId);
        Optional<User> user = userRepository.findById(userId);

        if (!checkIfProductIsPresentInLikedOrDislikedOfUser(user.get(),product.get())) {
            if(!removeLikeAndSave(user.get(),product.get())) {
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }
        addLikeAndSave(user.get(),product.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> dislikeProduct(Long productId, Long userId) {
        Optional<Product> product = productRepository.findById(productId);
        Optional<User> user = userRepository.findById(userId);

        if (!checkIfProductIsPresentInLikedOrDislikedOfUser(user.get(),product.get())) {
            if(!removeUnLikeAndSave(user.get(),product.get())) {
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }
        addDisLikeAndSave(user.get(),product.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public boolean isProductPresentInSet(Product product, Set set){
        return set.contains(product);
    }

    public Page<ProductDto> convertProductListToPage(List list, PageRequest pageRequest){
        int totalElements = list.size();
        int start = toIntExact(pageRequest.getOffset());
        int end = Math.min((start + pageRequest.getPageSize()), totalElements);

        List<ProductDto> pageList =  new ArrayList<>();
        if (start <= end) {
            pageList = list.subList(start, end);
        }

        return new PageImpl<>(pageList, pageRequest, totalElements);
    }

    public boolean checkIfProductIsPresentInLikedOrDislikedOfUser(User user, Product product){
        if (user.getLikedProducts().contains(product) || user.getDislikedProducts().contains(product)){
            return false;
        }
        return true;
    }

    private boolean addLikeAndSave(User user, Product product){
        if (product.getLikes()<0){
            return false;

        }
        user.getLikedProducts().add(product);
        userRepository.save(user);

        product.setLikes(product.getLikes()+1);
        productRepository.save(product);
        return true;
    }
    private boolean addDisLikeAndSave(User user, Product product){
        if (product.getUnlikes()<0){
            return false;

        }
        user.getDislikedProducts().add(product);
        userRepository.save(user);

        product.setUnlikes(product.getUnlikes()+1);
        productRepository.save(product);
        return true;
    }
    private boolean removeLikeAndSave(User user, Product product){
        if (product.getLikes()<0){
            return false;

        }
        product.setLikes(product.getLikes()-1);
        productRepository.save(product);

        user.getLikedProducts().remove(product);
        userRepository.save(user);
        return true;
    }
    private boolean removeUnLikeAndSave(User user, Product product){
        if (product.getUnlikes()<=0){
            return false;

        }
        product.setUnlikes(product.getUnlikes()-1);
        productRepository.save(product);

        user.getDislikedProducts().remove(product);
        userRepository.save(user);
        return true;
    }

}
