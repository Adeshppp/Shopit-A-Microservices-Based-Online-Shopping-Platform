package com.OnlineShopping.productservice.service;

import com.OnlineShopping.productservice.dto.ProductRequest;
import com.OnlineShopping.productservice.dto.ProductResponse;
import com.OnlineShopping.productservice.model.Product;
import com.OnlineShopping.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor // it will create a parameterized constructor with required parameters
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest){
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        productRepository.save(product);

        // todo: call updateProductsInInventory() in inventory micro service

//        log.info("Product "+product.getId()+" is saved");
        log.info("Product {} is saved", product.getId()); //places the value dynamically by using Slf4j
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::mapToProductResponse).toList();
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
