package com.artur.api.shopping.shopping_api.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.artur.api.shopping.shopping_api.models.Shop;

@Repository
public interface ShopRepository extends MongoRepository<Shop, String> {

    // para o endpoint: GET /shopping/shopByUser
    List<Shop> findByUserIdentifier(String userIdentifier);

    // para o endpoint: GET /shopping/shopByDate
    List<Shop> findAllByDateGreaterThanEqual(LocalDateTime date);

    // para o endpoint: GET /shopping/search
    List<Shop> findByDateBetweenAndTotalGreaterThanEqual(LocalDateTime startDate, LocalDateTime endDate, Float minValue);

    // para o endpoint: GET /shopping/product/{productIdentifier}
    List<Shop> findByItemsProductIdentifier(String productIdentifier);
    
    // para o endpoint: GET /shopping/report
    List<Shop> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate);

}