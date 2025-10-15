package com.artur.api.product.product_api.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.artur.api.product.product_api.models.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {


     // para o endpoint: GET /product/{productIdentifier}
    Product findByProductIdentifier(String productIdentifier);

    // para o endpoint: GET /product/category/{categoryId}
    List<Product> findByCategoryId(String categoryId);

}
