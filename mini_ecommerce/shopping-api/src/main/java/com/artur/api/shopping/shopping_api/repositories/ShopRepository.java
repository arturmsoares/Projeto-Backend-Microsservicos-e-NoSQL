package com.artur.api.shopping.shopping_api.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.artur.api.shopping.shopping_api.models.Shop;

@Repository
public interface ShopRepository extends MongoRepository<Shop, String>, ReportRepository {

    // para o endpoint: GET /shopping/shopByUser
    public List<Shop> findByUserIdentifier(String userIdentifier);

     // para o endpoint: GET /shopping/shopByDate
    public List<Shop> findAllByDateGreaterThanEqual(LocalDate date);


    //para o endpoint: GET /shopping/search
    //?0, ?1, ?2 são placeholders que representam os parâmetros do método, na ordem.
    @Query("{'date': {$gte: ?0, $lte: ?1}, 'total': {$gte: ?2}}")
    List<Shop> getShopByFilters(LocalDate dataInicio, LocalDate dataFim, Float valorMinimo);


     // para o endpoint: GET /shopping/product/{productIdentifier}
    @Query("{'items.productIdentifier': ?0}")
    List<Shop> findByItemsProductIdentifier(String productIdentifier);

}