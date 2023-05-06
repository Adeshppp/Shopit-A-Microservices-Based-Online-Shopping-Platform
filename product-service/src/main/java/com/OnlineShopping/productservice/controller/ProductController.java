package com.OnlineShopping.productservice.controller;


import com.OnlineShopping.productservice.dto.ProductRequest;
import com.OnlineShopping.productservice.dto.ProductResponse;
import com.OnlineShopping.productservice.dto.UpdatePriceRequest;
import com.OnlineShopping.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor // it will create a parameterized constructor with required parameters
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productRequest){
        productService.createProduct(productRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts(){
        return productService.getAllProducts();
    }

    // todo: add method to uodate the price of the product
    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updatePrice(@RequestBody UpdatePriceRequest updatePriceRequest){
        productService.updatePrice(updatePriceRequest);
    }
}
