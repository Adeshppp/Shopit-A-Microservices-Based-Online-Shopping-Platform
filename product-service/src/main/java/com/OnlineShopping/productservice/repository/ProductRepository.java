package com.OnlineShopping.productservice.repository;


import com.OnlineShopping.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
