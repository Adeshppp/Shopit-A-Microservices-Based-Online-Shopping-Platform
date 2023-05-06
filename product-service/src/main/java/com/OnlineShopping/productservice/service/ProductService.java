package com.OnlineShopping.productservice.service;

import com.OnlineShopping.productservice.dto.ProductRequest;
import com.OnlineShopping.productservice.dto.ProductResponse;
import com.OnlineShopping.productservice.dto.ProductToInventoryRequest;
import com.OnlineShopping.productservice.dto.UpdatePriceRequest;
import com.OnlineShopping.productservice.model.Product;
import com.OnlineShopping.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

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

        // call AddProductInInventory() in inventory micro-service
        WebClient webClient = WebClient.create("http://localhost:8082/api/inventory");
        ProductToInventoryRequest productToInventoryRequest = new ProductToInventoryRequest();
        productToInventoryRequest.setSkuCode(productRequest.getName());
        webClient.post()// post request
                .contentType(MediaType.APPLICATION_JSON)// set the content type of the request to JSON
                .body(BodyInserters.fromValue(productToInventoryRequest))// set the request body using the BodyInserters utility class
                .exchangeToMono(ClientResponse::toBodilessEntity)// to make the request
                .block();//  to wait for the response
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

    public void updatePrice(UpdatePriceRequest updatePriceRequest) {
        Optional<Product> product = productRepository.findById(updatePriceRequest.getId());
        Product updatedProduct = product.get();
        if(product.isPresent()){
            updatedProduct.setPrice(updatePriceRequest.getPrice());
            productRepository.save(updatedProduct);
            log.info("Product {} price is updated to {}", updatedProduct.getName(),updatePriceRequest.getPrice());
        }
        else log.error("Product with id {} not found!!!", updatePriceRequest.getId());
    }
}
